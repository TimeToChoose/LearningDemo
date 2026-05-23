package com.shineofeidos.demo.controller

import com.shineofeidos.demo.auth.PublicApi
import com.shineofeidos.demo.auth.UserContext
import com.shineofeidos.demo.model.ApiResponse
import com.shineofeidos.demo.model.LoginRequest
import com.shineofeidos.demo.model.LogoutRequest
import com.shineofeidos.demo.model.RefreshRequest
import com.shineofeidos.demo.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "登录鉴权（公开接口，无需 Bearer Token）")
@RestController
@RequestMapping("/api/auth")
@PublicApi
class AuthController(
    private val authService: AuthService,
) {
    @Operation(summary = "用户登录", description = "用户名密码登录，返回 accessToken 与 refreshToken")
    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest) =
        ApiResponse.ok(authService.login(request))

    @Operation(summary = "刷新 Token", description = "使用 refreshToken 换取新的 accessToken")
    @PostMapping("/refresh")
    fun refresh(@Valid @RequestBody request: RefreshRequest) =
        ApiResponse.ok(authService.refresh(request))

    @Operation(summary = "退出登录", description = "撤销 refreshToken，需 Bearer accessToken")
    @PostMapping("/logout")
    fun logout(
        request: HttpServletRequest,
        @RequestBody body: LogoutRequest?,
    ): ApiResponse<Map<String, Boolean>> {
        val authUser = UserContext.require(request)
        authService.logout(authUser.userId, body?.refreshToken)
        return ApiResponse.ok(mapOf("success" to true))
    }
}
