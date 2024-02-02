package com.studentcenter.weave.application.service.util.impl.strategy

import com.studentcenter.weave.application.vo.UserTokenClaims
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider

class OpenIdTokenResolveStrategyFactoryStub: OpenIdTokenResolveStrategyFactory {

    private val user = UserFixtureFactory.create()

    override fun getStrategy(socialLoginProvider: SocialLoginProvider): OpenIdTokenResolveStrategy {
        return OpenIdTokenResolveStrategy {
            UserTokenClaims.IdToken(
                nickname = user.nickname,
                email = user.email,
            )
        }
    }
}
