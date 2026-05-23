package com.shineofeidos.demo.exception

class BusinessException(
    val code: Int,
    message: String,
) : RuntimeException(message)
