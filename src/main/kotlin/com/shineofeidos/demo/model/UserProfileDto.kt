package com.shineofeidos.demo.model

import java.time.LocalDateTime

data class UserProfileDto(
    val id: Long,
    val username: String,
    val nickname: String,
    val avatar: String,
    val bio: String,
    val createdAt: LocalDateTime?,
)
