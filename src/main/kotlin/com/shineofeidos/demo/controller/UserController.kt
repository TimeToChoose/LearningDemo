package com.shineofeidos.demo.controller

import com.shineofeidos.demo.auth.UserContext
import com.shineofeidos.demo.model.ApiResponse
import com.shineofeidos.demo.model.UserProfileDto
import com.shineofeidos.demo.model.UserProfileUpdateRequest
import com.shineofeidos.demo.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "User", description = "个人信息（需 Bearer Token）")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
) {
    @Operation(summary = "获取个人资料")
    @GetMapping("/profile")
    fun getProfile(request: HttpServletRequest): ApiResponse<UserProfileDto> {
        val authUser = UserContext.require(request)
        return ApiResponse.ok(userService.getProfile(authUser.userId))
    }

    @Operation(summary = "更新个人资料")
    @PutMapping("/profile")
    fun updateProfile(
        request: HttpServletRequest,
        @Valid @RequestBody body: UserProfileUpdateRequest,
    ): ApiResponse<UserProfileDto> {
        val authUser = UserContext.require(request)
        return ApiResponse.ok(userService.updateProfile(authUser.userId, body))
    }
}
