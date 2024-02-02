package com.studentcenter.weave.application.service.util.impl.strategy

import com.studentcenter.weave.application.common.properties.OpenIdProperties
import com.studentcenter.weave.application.vo.UserTokenClaims
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.security.jwt.util.JwtTokenProvider
import com.studentcenter.weave.support.security.jwt.vo.JwtClaims
import org.springframework.stereotype.Component
import java.net.URL

@Component
class KakaoOpenIdTokenResolveStrategy(
    private val openIdProperties: OpenIdProperties
) : OpenIdTokenResolveStrategy {

    override fun resolveIdToken(idToken: String): UserTokenClaims.IdToken {
        val jwksUri = URL(this.openIdProperties.providers.getValue(SocialLoginProvider.KAKAO).jwksUri)
        val result: JwtClaims = JwtTokenProvider.verifyJwksBasedToken(idToken, jwksUri).getOrThrow()

        val nickname = (result.customClaims["nickname"] as String)
        val email = result.customClaims["email"] as String

        return UserTokenClaims.IdToken(
            nickname = Nickname(adjustNickname(nickname)),
            email = Email(email),
        )
    }

    private fun adjustNickname(nickname: String): String {
        return if (nickname.length > Nickname.MAX_LENGTH) nickname.substring(0, Nickname.MAX_LENGTH) else nickname
    }

}
