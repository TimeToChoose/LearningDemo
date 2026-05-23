package com.shineofeidos.demo.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class EchoRequest(
    @field:NotBlank(message = "message 不能为空")
    @field:Size(max = 500)
    val message: String,
)
