package com.shineofeidos.demo.model

data class ProductPageResult(
    val items: List<MallProduct>,
    val page: Int,
    val size: Int,
    val total: Long,
    val totalPages: Int,
)
