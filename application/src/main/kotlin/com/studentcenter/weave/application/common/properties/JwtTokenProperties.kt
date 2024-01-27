package com.studentcenter.weave.application.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "auth.jwt")
data class JwtTokenProperties(
    val issuer: String,
    val access: TokenProperties,
    val refresh: TokenProperties,
    val register: TokenProperties,
) {

    data class TokenProperties(
        val expireSeconds: Long,
        val secret: String
    )

}
