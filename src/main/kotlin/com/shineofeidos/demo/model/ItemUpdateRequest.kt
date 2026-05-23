package com.shineofeidos.demo.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ItemUpdateRequest(
    @field:NotBlank(message = "name 不能为空")
    @field:Size(max = 100)
    val name: String,

    @field:NotBlank(message = "category 不能为空")
    @field:Size(max = 50)
    val category: String,
)
