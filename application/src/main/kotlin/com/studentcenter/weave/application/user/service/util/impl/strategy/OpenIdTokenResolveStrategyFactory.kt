package com.studentcenter.weave.application.user.service.util.impl.strategy

import com.studentcenter.weave.domain.user.enums.SocialLoginProvider

interface OpenIdTokenResolveStrategyFactory {

    fun getStrategy(socialLoginProvider: SocialLoginProvider): OpenIdTokenResolveStrategy

}
