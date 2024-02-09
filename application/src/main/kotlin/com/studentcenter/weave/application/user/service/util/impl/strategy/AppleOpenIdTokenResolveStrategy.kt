package com.studentcenter.weave.application.user.service.util.impl.strategy

import com.studentcenter.weave.application.common.properties.OpenIdProperties
import com.studentcenter.weave.application.user.vo.UserTokenClaims
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.security.jwt.util.JwtTokenProvider
import com.studentcenter.weave.support.security.jwt.vo.JwtClaims
import org.springframework.stereotype.Component
import java.net.URL

@Component
class AppleOpenIdTokenResolveStrategy(
    private val openIdProperties: OpenIdProperties
) : OpenIdTokenResolveStrategy {

    companion object {
        const val DEFAULT_NICKNAME = ""
    }

    override fun resolveIdToken(idToken: String): UserTokenClaims.IdToken {
        val jwksUri =
            URL(this.openIdProperties.providers.getValue(SocialLoginProvider.APPLE).jwksUri)
        val result: JwtClaims = JwtTokenProvider.verifyJwksBasedToken(idToken, jwksUri).getOrThrow()

        val email = result.customClaims["email"] as String

        return UserTokenClaims.IdToken(
            nickname = Nickname(DEFAULT_NICKNAME),
            email = Email(email),
        )
    }
}
