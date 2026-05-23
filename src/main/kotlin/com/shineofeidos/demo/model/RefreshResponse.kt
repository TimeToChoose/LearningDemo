package com.shineofeidos.demo.model

data class RefreshResponse(
    val accessToken: String,
    val expiresIn: Long,
)
