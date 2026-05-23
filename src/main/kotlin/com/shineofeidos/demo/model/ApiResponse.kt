package com.shineofeidos.demo.model

data class ApiResponse<T>(
    val code: Int,
    val message: String,
    val data: T?,
) {
    companion object {
        fun <T> ok(data: T, message: String = "success"): ApiResponse<T> =
            ApiResponse(code = 0, message = message, data = data)

        fun <T> fail(code: Int, message: String): ApiResponse<T> =
            ApiResponse(code = code, message = message, data = null)
    }
}
