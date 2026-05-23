package com.shineofeidos.demo.model

import java.time.LocalDateTime

data class Item(
    val id: Long? = null,
    val name: String,
    val category: String,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)
