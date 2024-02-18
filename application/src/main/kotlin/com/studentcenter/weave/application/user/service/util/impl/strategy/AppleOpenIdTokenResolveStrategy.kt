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

    val emailDelimiter: String = "@"

    override fun resolveIdToken(idToken: String): UserTokenClaims.IdToken {
        val jwksUri =
            URL(this.openIdProperties.providers.getValue(SocialLoginProvider.APPLE).jwksUri)
        val result: JwtClaims = JwtTokenProvider.verifyJwksBasedToken(idToken, jwksUri).getOrThrow()

        val email = result.customClaims["email"] as String

        return UserTokenClaims.IdToken(
            nickname = Nickname(extractNicknameFromEmail(email)),
            email = Email(email),
        )
    }

    private fun extractNicknameFromEmail(email: String): String {
        val nickname: String = email.substringBefore(emailDelimiter)

        return if (nickname.length <= Nickname.MAX_LENGTH) nickname
        else nickname.substring(0, Nickname.MAX_LENGTH)
    }
}
