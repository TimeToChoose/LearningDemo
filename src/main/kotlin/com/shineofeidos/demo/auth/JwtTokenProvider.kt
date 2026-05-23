package com.shineofeidos.demo.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") secret: String,
    @Value("\${jwt.access-expire-minutes}") private val accessExpireMinutes: Long,
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8))

    fun createAccessToken(userId: Long, username: String): String {
        val now = Date()
        val expiry = Date(now.time + accessExpireMinutes * 60 * 1000)
        return Jwts.builder()
            .subject(userId.toString())
            .claim("username", username)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(key)
            .compact()
    }

    fun parseAccessToken(token: String): AuthUser? {
        return try {
            val claims: Claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload
            val userId = claims.subject.toLong()
            val username = claims["username"] as String
            AuthUser(userId, username)
        } catch (_: Exception) {
            null
        }
    }

    fun accessTokenExpiresInSeconds(): Long = accessExpireMinutes * 60
}
