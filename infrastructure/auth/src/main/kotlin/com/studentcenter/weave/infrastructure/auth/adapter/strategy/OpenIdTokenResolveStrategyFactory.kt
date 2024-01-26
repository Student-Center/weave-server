package com.studentcenter.weave.infrastructure.auth.adapter.strategy

import com.studentcenter.weave.domain.enum.SocialLoginProvider
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class OpenIdTokenResolveStrategyFactory (
    private val applicationContext: ApplicationContext
){

    fun getStrategy(socialLoginProvider: SocialLoginProvider): OpenIdTokenResolveStrategy {
        return when (socialLoginProvider) {
            SocialLoginProvider.APPLE -> applicationContext.getBean(AppleOpenIdTokenResolveStrategy::class.java)
            SocialLoginProvider.KAKAO -> applicationContext.getBean(KakaoOpenIdTokenResolveStrategy::class.java)
        }
    }

}
