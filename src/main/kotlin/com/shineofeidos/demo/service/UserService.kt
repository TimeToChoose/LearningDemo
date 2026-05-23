package com.shineofeidos.demo.service

import com.shineofeidos.demo.exception.BusinessException
import com.shineofeidos.demo.mapper.UserMapper
import com.shineofeidos.demo.model.SysUser
import com.shineofeidos.demo.model.UserProfileDto
import com.shineofeidos.demo.model.UserProfileUpdateRequest
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userMapper: UserMapper,
) {
    fun getProfile(userId: Long): UserProfileDto {
        val user = userMapper.findById(userId)
            ?: throw BusinessException(404, "User not found")
        return user.toProfileDto()
    }

    fun updateProfile(userId: Long, request: UserProfileUpdateRequest): UserProfileDto {
        val existing = userMapper.findById(userId)
            ?: throw BusinessException(404, "User not found")
        val updated = existing.copy(
            nickname = request.nickname ?: existing.nickname,
            avatar = request.avatar ?: existing.avatar,
            bio = request.bio ?: existing.bio,
        )
        userMapper.updateProfile(updated)
        return userMapper.findById(userId)!!.toProfileDto()
    }

    fun findById(userId: Long): SysUser? = userMapper.findById(userId)

    private fun SysUser.toProfileDto() = UserProfileDto(
        id = id!!,
        username = username,
        nickname = nickname,
        avatar = avatar,
        bio = bio,
        createdAt = createdAt,
    )
}
