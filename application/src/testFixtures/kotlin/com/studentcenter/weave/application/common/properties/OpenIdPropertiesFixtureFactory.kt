package com.studentcenter.weave.application.common.properties

import com.studentcenter.weave.domain.user.enums.SocialLoginProvider

class OpenIdPropertiesFixtureFactory {

    companion object {
        fun create(
            socialLoginProvider: SocialLoginProvider,
            providers: Map<SocialLoginProvider, OpenIdProperties.Properties> = mapOf(
                socialLoginProvider to createProperties(),
            ),
        ): OpenIdProperties {
            return OpenIdProperties(
                providers = providers,
            )
        }

        fun createProperties(
            jwksUri: String = "https://test.com",
        ): OpenIdProperties.Properties {
            return OpenIdProperties.Properties(
                jwksUri = jwksUri,
            )
        }
    }

}
