package com.studentcenter.weave.infrastructure.auth.adapter.strategy

import com.studentcenter.weave.application.vo.UserTokenClaims

interface OpenIdTokenResolveStrategy {

    fun resolveIdToken(idToken: String): UserTokenClaims.IdToken

}
