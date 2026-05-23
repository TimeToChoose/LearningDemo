package com.shineofeidos.demo.model

import jakarta.validation.constraints.Size

data class UserProfileUpdateRequest(
    @field:Size(max = 50)
    val nickname: String? = null,

    @field:Size(max = 500)
    val avatar: String? = null,

    @field:Size(max = 500)
    val bio: String? = null,
)
