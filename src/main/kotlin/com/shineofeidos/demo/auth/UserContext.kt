package com.shineofeidos.demo.auth

data class AuthUser(
    val userId: Long,
    val username: String,
)

object UserContext {
  const val REQUEST_ATTR = "authUser"

  fun get(request: jakarta.servlet.http.HttpServletRequest): AuthUser? =
      request.getAttribute(REQUEST_ATTR) as? AuthUser

  fun require(request: jakarta.servlet.http.HttpServletRequest): AuthUser =
      get(request) ?: throw com.shineofeidos.demo.exception.UnauthorizedException()
}
