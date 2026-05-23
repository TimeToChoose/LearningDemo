package com.shineofeidos.demo.model

import java.time.LocalDateTime

data class DemoMessage(
    val id: Long? = null,
    val message: String,
    val createdAt: LocalDateTime? = null,
)
