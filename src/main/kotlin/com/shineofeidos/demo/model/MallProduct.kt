package com.shineofeidos.demo.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class MallProduct(
    val id: Long? = null,
    val name: String,
    val price: BigDecimal,
    val coverUrl: String,
    val category: String,
    val stock: Int,
    val description: String = "",
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)
