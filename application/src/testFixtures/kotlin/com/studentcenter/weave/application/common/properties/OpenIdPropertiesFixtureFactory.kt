package com.studentcenter.weave.application.common.properties

import com.studentcenter.weave.domain.enum.SocialLoginProvider

class OpenIdPropertiesFixtureFactory {

    companion object {
        fun create(
            providers: Map<SocialLoginProvider, OpenIdProperties.Properties> = mapOf(
                SocialLoginProvider.KAKAO to createProperties(),
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
