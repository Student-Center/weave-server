package com.studentcenter.weave.application.service.util.impl.strategy

import com.studentcenter.weave.domain.enum.SocialLoginProvider

interface OpenIdTokenResolveStrategyFactory {

    fun getStrategy(socialLoginProvider: SocialLoginProvider): OpenIdTokenResolveStrategy

}
