package com.studentcenter.weave.application.user.service.util.impl.strategy

import com.studentcenter.weave.application.user.vo.UserTokenClaims

fun interface OpenIdTokenResolveStrategy {

    fun resolveIdToken(idToken: String): UserTokenClaims.IdToken

}
