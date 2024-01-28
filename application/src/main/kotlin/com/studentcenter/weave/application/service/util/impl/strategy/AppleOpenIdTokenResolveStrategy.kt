package com.studentcenter.weave.application.service.util.impl.strategy

import com.studentcenter.weave.application.vo.UserTokenClaims
import org.springframework.stereotype.Component

@Component
class AppleOpenIdTokenResolveStrategy : OpenIdTokenResolveStrategy {

    override fun resolveIdToken(idToken: String): UserTokenClaims.IdToken {
        TODO("Not yet implemented")
    }

}
