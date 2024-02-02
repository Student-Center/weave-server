package com.studentcenter.weave.application.user.service.util.impl.strategy

import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class OpenIdTokenResolveStrategyFactoryImpl (
    private val applicationContext: ApplicationContext
): OpenIdTokenResolveStrategyFactory {

    override fun getStrategy(socialLoginProvider: SocialLoginProvider): OpenIdTokenResolveStrategy {
        return when (socialLoginProvider) {
            SocialLoginProvider.APPLE -> applicationContext.getBean(AppleOpenIdTokenResolveStrategy::class.java)
            SocialLoginProvider.KAKAO -> applicationContext.getBean(KakaoOpenIdTokenResolveStrategy::class.java)
        }
    }

}
