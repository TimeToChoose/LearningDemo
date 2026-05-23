package com.shineofeidos.demo.model

import jakarta.validation.constraints.NotBlank

data class RefreshRequest(
    @field:NotBlank(message = "refreshToken is required")
    val refreshToken: String,
)
