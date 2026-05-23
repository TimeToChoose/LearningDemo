package com.shineofeidos.demo.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Component
class JwtAuthFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val handlerMapping: RequestMappingHandlerMapping,
) : OncePerRequestFilter() {

    private val swaggerPrefixes = listOf(
        "/swagger-ui",
        "/v3/api-docs",
        "/api-docs",
    )

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val path = request.requestURI

        if (isPublicPath(path) || isPublicHandler(request)) {
            filterChain.doFilter(request, response)
            return
        }

        if (requiresAuth(path)) {
            val authHeader = request.getHeader("Authorization")
            if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
                writeUnauthorized(response)
                return
            }
            val token = authHeader.removePrefix("Bearer ").trim()
            val authUser = jwtTokenProvider.parseAccessToken(token)
            if (authUser == null) {
                writeUnauthorized(response)
                return
            }
            request.setAttribute(UserContext.REQUEST_ATTR, authUser)
        }

        filterChain.doFilter(request, response)
    }

    private fun isPublicPath(path: String): Boolean {
        if (swaggerPrefixes.any { path.startsWith(it) }) return true
        if (path == "/api/health" || path == "/api/hello") return true
        if (path == "/api/auth/login" || path == "/api/auth/refresh") return true
        if (path.startsWith("/api/mall")) return true
        if (path.startsWith("/api/videos")) return true
        if (path.startsWith("/api/items") || path.startsWith("/api/echo") || path == "/api/messages") return true
        return false
    }

    private fun requiresAuth(path: String): Boolean =
        path.startsWith("/api/user") || path == "/api/auth/logout"

    private fun isPublicHandler(request: HttpServletRequest): Boolean {
        return try {
            val handler = handlerMapping.getHandler(request)?.handler
            if (handler !is HandlerMethod) return false
            handler.hasMethodAnnotation(PublicApi::class.java) ||
                handler.beanType.isAnnotationPresent(PublicApi::class.java)
        } catch (_: Exception) {
            false
        }
    }

    private fun writeUnauthorized(response: HttpServletResponse) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.writer.write("""{"code":401,"message":"Unauthorized","data":null}""")
    }
}
