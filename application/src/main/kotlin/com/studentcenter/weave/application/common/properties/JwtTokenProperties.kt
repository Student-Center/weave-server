package com.studentcenter.weave.application.common.properties

import com.studentcenter.weave.domain.enum.SocialLoginProvider
import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URL

@ConfigurationProperties(value = "user.jwt")
data class JwtTokenProperties(
    val access: TokenProperties,
    val refresh: TokenProperties,
    val socialLoginProvider: Map<SocialLoginProvider, Provider>,
) {

    data class TokenProperties(
        val expireSeconds: Long,
        val secret: String
    )

    data class Provider(
        val jwksUri: URL,
    )

}
