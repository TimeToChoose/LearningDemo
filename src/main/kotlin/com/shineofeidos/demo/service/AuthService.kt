package com.shineofeidos.demo.service

import com.shineofeidos.demo.auth.JwtTokenProvider
import com.shineofeidos.demo.exception.BusinessException
import com.shineofeidos.demo.mapper.RefreshTokenMapper
import com.shineofeidos.demo.mapper.UserMapper
import com.shineofeidos.demo.model.LoginRequest
import com.shineofeidos.demo.model.LoginResponse
import com.shineofeidos.demo.model.RefreshRequest
import com.shineofeidos.demo.model.RefreshResponse
import com.shineofeidos.demo.model.RefreshTokenRecord
import com.shineofeidos.demo.model.UserSummaryDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest
import java.time.LocalDateTime
import java.util.UUID

@Service
class AuthService(
    private val userMapper: UserMapper,
    private val refreshTokenMapper: RefreshTokenMapper,
    private val jwtTokenProvider: JwtTokenProvider,
    @Value("\${jwt.refresh-expire-days}") private val refreshExpireDays: Long,
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    @Transactional
    fun login(request: LoginRequest): LoginResponse {
        val user = userMapper.findByUsername(request.username)
            ?: throw BusinessException(401, "Invalid username or password")
        if (!passwordEncoder.matches(request.password, user.passwordHash)) {
            throw BusinessException(401, "Invalid username or password")
        }
        val userId = user.id!!
        val accessToken = jwtTokenProvider.createAccessToken(userId, user.username)
        val refreshToken = issueRefreshToken(userId)
        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresIn = jwtTokenProvider.accessTokenExpiresInSeconds(),
            user = user.toSummary(),
        )
    }

    @Transactional
    fun refresh(request: RefreshRequest): RefreshResponse {
        val hash = hashToken(request.refreshToken)
        val record = refreshTokenMapper.findValidByHash(hash)
            ?: throw BusinessException(401, "Invalid or expired refresh token")
        val user = userMapper.findById(record.userId)
            ?: throw BusinessException(401, "User not found")
        val accessToken = jwtTokenProvider.createAccessToken(user.id!!, user.username)
        return RefreshResponse(
            accessToken = accessToken,
            expiresIn = jwtTokenProvider.accessTokenExpiresInSeconds(),
        )
    }

    @Transactional
    fun logout(userId: Long, refreshToken: String?) {
        if (!refreshToken.isNullOrBlank()) {
            refreshTokenMapper.revokeByHash(hashToken(refreshToken))
        } else {
            refreshTokenMapper.revokeAllByUserId(userId)
        }
    }

    private fun issueRefreshToken(userId: Long): String {
        val raw = UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "")
        val record = RefreshTokenRecord(
            userId = userId,
            tokenHash = hashToken(raw),
            expiresAt = LocalDateTime.now().plusDays(refreshExpireDays),
            revoked = false,
        )
        refreshTokenMapper.insert(record)
        return raw
    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(token.toByteArray()).joinToString("") { "%02x".format(it) }
    }

    private fun com.shineofeidos.demo.model.SysUser.toSummary() = UserSummaryDto(
        id = id!!,
        username = username,
        nickname = nickname,
        avatar = avatar,
    )
}
