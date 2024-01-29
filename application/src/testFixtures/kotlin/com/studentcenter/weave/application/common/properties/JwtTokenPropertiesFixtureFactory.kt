package com.studentcenter.weave.application.common.properties

class JwtTokenPropertiesFixtureFactory {

    companion object {

        fun create(): JwtTokenProperties {
            val accessToken = JwtTokenProperties.TokenProperties(
                secret = "secret",
                expireSeconds = 1000L,
            )
            val refreshToken = JwtTokenProperties.TokenProperties(
                secret = "secret",
                expireSeconds = 1000L,
            )
            val registerToken = JwtTokenProperties.TokenProperties(
                secret = "secret",
                expireSeconds = 1000L,
            )

            return JwtTokenProperties(
                issuer = "issuer",
                access = accessToken,
                refresh = refreshToken,
                register = registerToken,
            )
        }
    }

}
