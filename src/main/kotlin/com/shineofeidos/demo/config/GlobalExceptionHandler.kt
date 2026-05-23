package com.shineofeidos.demo.config

import com.shineofeidos.demo.exception.BusinessException
import com.shineofeidos.demo.exception.UnauthorizedException
import com.shineofeidos.demo.model.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidation(ex: MethodArgumentNotValidException): ApiResponse<Nothing> {
        val msg = ex.bindingResult.fieldErrors
            .joinToString("; ") { "${it.field}: ${it.defaultMessage}" }
        return ApiResponse.fail(400, msg)
    }

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.OK)
    fun handleBusiness(ex: BusinessException): ApiResponse<Nothing> =
        ApiResponse.fail(ex.code, ex.message ?: "Business error")

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorized(ex: UnauthorizedException): ApiResponse<Nothing> =
        ApiResponse.fail(401, ex.message ?: "Unauthorized")
}
