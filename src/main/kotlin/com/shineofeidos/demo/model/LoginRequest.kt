package com.shineofeidos.demo.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank(message = "username is required")
    @field:Size(max = 50)
    val username: String,

    @field:NotBlank(message = "password is required")
    @field:Size(min = 6, max = 50)
    val password: String,
)
