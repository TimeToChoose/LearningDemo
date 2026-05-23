package com.shineofeidos.demo.model

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val user: UserSummaryDto,
)
