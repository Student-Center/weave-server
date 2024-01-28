package com.studentcenter.weave.application.service.util.impl.strategy

import com.studentcenter.weave.application.vo.UserTokenClaims

interface OpenIdTokenResolveStrategy {

    fun resolveIdToken(idToken: String): UserTokenClaims.IdToken

}
