package com.studentcenter.weave.bootstrap.common.security.annotation

import io.swagger.v3.oas.annotations.security.SecurityRequirement

/**
 * 인증된 사용자만 접근 가능한 API에 사용하는 어노테이션
 */
@SecurityRequirement(name = "AccessToken")
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Secured
