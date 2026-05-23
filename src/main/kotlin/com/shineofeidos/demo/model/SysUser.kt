package com.shineofeidos.demo.model

import java.time.LocalDateTime

data class SysUser(
    val id: Long? = null,
    val username: String,
    val passwordHash: String,
    val nickname: String,
    val avatar: String = "",
    val bio: String = "",
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)
