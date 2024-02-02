package com.studentcenter.weave.bootstrap.common.security.annotation

/**
 * 인증된 사용자만 접근 가능한 API에 사용하는 어노테이션
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Secured
