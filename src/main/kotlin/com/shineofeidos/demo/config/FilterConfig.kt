package com.shineofeidos.demo.config

import com.shineofeidos.demo.auth.JwtAuthFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class FilterConfig {

    @Bean
    fun jwtAuthFilterRegistration(filter: JwtAuthFilter): FilterRegistrationBean<JwtAuthFilter> {
        val registration = FilterRegistrationBean(filter)
        registration.order = Ordered.HIGHEST_PRECEDENCE + 10
        registration.addUrlPatterns("/api/*")
        return registration
    }
}
