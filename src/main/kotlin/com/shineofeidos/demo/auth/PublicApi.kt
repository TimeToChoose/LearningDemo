package com.shineofeidos.demo.auth

/**
 * 标记 Controller 类或方法为公开接口，跳过 JWT 鉴权。
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PublicApi
