package com.studentcenter.weave.application.user.service.util.impl.strategy

import com.studentcenter.weave.application.user.vo.UserTokenClaims
import org.springframework.stereotype.Component

@Component
class AppleOpenIdTokenResolveStrategy : OpenIdTokenResolveStrategy {

    override fun resolveIdToken(idToken: String): UserTokenClaims.IdToken {
        TODO("Not yet implemented")
    }

}
