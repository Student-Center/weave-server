package com.studentcenter.weave.infrastructure.auth.adapter.strategy

import com.studentcenter.weave.application.vo.UserTokenClaims
import org.springframework.stereotype.Component

@Component
class AppleOpenIdTokenResolveStrategy : OpenIdTokenResolveStrategy {

    override fun resolveIdToken(idToken: String): UserTokenClaims.IdToken {
        TODO("Not yet implemented")
    }

}
