package com.shineofeidos.demo.model

import java.time.LocalDateTime

data class RefreshTokenRecord(
    val id: Long? = null,
    val userId: Long,
    val tokenHash: String,
    val expiresAt: LocalDateTime,
    val revoked: Boolean = false,
    val createdAt: LocalDateTime? = null,
)
