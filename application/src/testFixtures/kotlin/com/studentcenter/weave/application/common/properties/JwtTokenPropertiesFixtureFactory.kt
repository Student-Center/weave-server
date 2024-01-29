package com.studentcenter.weave.application.common.properties

class JwtTokenPropertiesFixtureFactory {

    companion object {

        fun create(
            issuer: String = "issuer",
            accessToken: JwtTokenProperties.TokenProperties = createTokenProperties(),
            refreshToken: JwtTokenProperties.TokenProperties = createTokenProperties(),
            registerToken: JwtTokenProperties.TokenProperties = createTokenProperties(),
        ): JwtTokenProperties {
            return JwtTokenProperties(
                issuer = issuer,
                access = accessToken,
                refresh = refreshToken,
                register = registerToken,
            )
        }

        fun createTokenProperties(
            secret: String = "secret",
            expireSeconds: Long = 1000L,
        ): JwtTokenProperties.TokenProperties {
            return JwtTokenProperties.TokenProperties(
                secret = secret,
                expireSeconds = expireSeconds,
            )
        }
    }

}
