package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.properties.JwtTokenPropertiesFixtureFactory
import com.studentcenter.weave.application.user.port.inbound.SocialLogin
import com.studentcenter.weave.application.user.port.outbound.UserAuthInfoRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserRefreshTokenRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.UserAuthInfoDomainServiceImpl
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.service.util.impl.UserTokenServiceImpl
import com.studentcenter.weave.application.user.service.util.impl.strategy.OpenIdTokenResolveStrategyFactoryStub
import com.studentcenter.weave.domain.user.entity.UserAuthInfoFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import io.kotest.assertions.asClue
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeTypeOf

@DisplayName("SocialLoginTest")
class SocialLoginTest : DescribeSpec({

    val userRepositorySpy = UserRepositorySpy()
    val userAuthInfoRepositorySpy = UserAuthInfoRepositorySpy()

    val sut = SocialLoginService(
        userTokenService = UserTokenServiceImpl(
            jwtTokenProperties = JwtTokenPropertiesFixtureFactory.create(),
            userRefreshTokenRepository = UserRefreshTokenRepositorySpy(),
            openIdTokenResolveStrategyFactory = OpenIdTokenResolveStrategyFactoryStub(),
        ),
        userDomainService = UserDomainServiceImpl(userRepositorySpy),
        userAuthInfoDomainService = UserAuthInfoDomainServiceImpl(userAuthInfoRepositorySpy),
    )

    afterTest {
        userRepositorySpy.clear()
        userAuthInfoRepositorySpy.clear()
    }

    describe("UserSocialLoginApplicationService") {
        enumValues<SocialLoginProvider>().forEach { socialLoginProvider ->
            context("idToken-${socialLoginProvider} 에 해당하는 회원이 존재하는 경우") {
                it("로그인 토큰을 응답한다 : ${socialLoginProvider.name}") {
                    // arrange
                    UserFixtureFactory.create()
                        .also { userRepositorySpy.save(it) }
                        .asClue { UserAuthInfoFixtureFactory.create(it, socialLoginProvider) }
                        .let { userAuthInfoRepositorySpy.save(it) }

                    val idToken = "idToken"
                    val command = SocialLogin.Command(
                        socialLoginProvider = socialLoginProvider,
                        idToken = idToken,
                    )

                    // act
                    val result: SocialLogin.Result = sut.invoke(command)

                    // assert
                    result.shouldBeTypeOf<SocialLogin.Result.Success>()
                    result.accessToken.shouldBeTypeOf<String>()
                    result.refreshToken.shouldBeTypeOf<String>()
                }
            }
        }

        enumValues<SocialLoginProvider>().forEach { socialLoginProvider ->
            context("id token에 해당하는 회원이 존재하지 않는 경우") {
                it("회원 가입 토큰을 응답한다 : ${socialLoginProvider.name}") {
                    // arrange
                    val idToken = "idToken"
                    val command =
                        SocialLogin.Command(
                            socialLoginProvider = socialLoginProvider,
                            idToken = idToken,
                        )

                    // act
                    val result: SocialLogin.Result =
                        sut.invoke(command)

                    // assert
                    result.shouldBeTypeOf<SocialLogin.Result.NotRegistered>()
                    result.registerToken.shouldBeTypeOf<String>()
                }
            }
        }
    }

})
