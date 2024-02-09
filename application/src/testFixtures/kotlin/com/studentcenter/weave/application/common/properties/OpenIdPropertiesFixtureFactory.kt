package com.studentcenter.weave.application.common.properties

import com.studentcenter.weave.domain.user.enums.SocialLoginProvider

class OpenIdPropertiesFixtureFactory {

    companion object {
        fun create(
            providerType : String,
            providers: Map<SocialLoginProvider, OpenIdProperties.Properties> = mapOf(
                SocialLoginProvider.valueOf(providerType) to createProperties(),
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
