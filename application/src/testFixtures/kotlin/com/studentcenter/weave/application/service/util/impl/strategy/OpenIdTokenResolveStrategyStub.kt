package com.studentcenter.weave.application.service.util.impl.strategy

import com.studentcenter.weave.application.vo.UserTokenClaims
import com.studentcenter.weave.domain.entity.UserFixtureFactory

class OpenIdTokenResolveStrategyStub : OpenIdTokenResolveStrategy {

    private val user = UserFixtureFactory.create()

    override fun resolveIdToken(idToken: String): UserTokenClaims.IdToken {
        return UserTokenClaims.IdToken(
            nickname = user.nickname,
            email = user.email,
        )
    }

}
