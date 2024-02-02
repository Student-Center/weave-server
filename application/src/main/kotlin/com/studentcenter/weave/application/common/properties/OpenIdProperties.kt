package com.studentcenter.weave.application.common.properties

import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "auth.open-id")
data class OpenIdProperties(
    val providers: Map<SocialLoginProvider, Properties>
) {
    data class Properties(
        val jwksUri: String,
    )
}
