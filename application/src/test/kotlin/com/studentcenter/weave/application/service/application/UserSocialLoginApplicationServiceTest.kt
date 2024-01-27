package com.studentcenter.weave.application.service.application

import com.studentcenter.weave.application.port.inbound.UserSocialLoginUseCase
import com.studentcenter.weave.application.service.util.UserTokenServiceStub
import com.studentcenter.weave.application.service.domain.UserAuthInfoDomainService
import com.studentcenter.weave.application.service.domain.UserDomainServiceStub
import com.studentcenter.weave.domain.entity.UserAuthInfo
import com.studentcenter.weave.domain.entity.UserAuthInfoFixtureFactory
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.every
import io.mockk.mockk

class UserSocialLoginApplicationServiceTest : DescribeSpec({

    describe("UserSocialLoginApplicationService") {

        val authInfoDomainServiceMock: UserAuthInfoDomainService =
            mockk<UserAuthInfoDomainService>()
        val userAuthInfoFixture: UserAuthInfo = UserAuthInfoFixtureFactory.create()
        val sut = UserSocialLoginApplicationService(
            userTokenService = UserTokenServiceStub(),
            userDomainService = UserDomainServiceStub(),
            userAuthInfoDomainService = authInfoDomainServiceMock,
        )

        context("소셜 로그인 성공시") {

            every {
                authInfoDomainServiceMock.findByEmail(userAuthInfoFixture.email)
            } returns UserAuthInfoFixtureFactory.create()

            enumValues<SocialLoginProvider>().forEach { socialLoginProvider ->

                it("Access Token과 Refresh Token을 응답한다 : ${socialLoginProvider.name}") {
                    // arrange
                    val idToken = "idToken"
                    val command = UserSocialLoginUseCase.Command(
                        socialLoginProvider = socialLoginProvider,
                        idToken = idToken,
                    )

                    // act
                    val result: UserSocialLoginUseCase.Result = sut.invoke(command)

                    // assert
                    result.shouldBeTypeOf<UserSocialLoginUseCase.Result.Success>()
                    result.accessToken.shouldBeTypeOf<String>()
                    result.refreshToken.shouldBeTypeOf<String>()
                }
            }
        }

        context("회원이 존재하지 않는 경우") {

            every { authInfoDomainServiceMock.findByEmail(userAuthInfoFixture.email) } returns null

            enumValues<SocialLoginProvider>().forEach { socialLoginProvider ->

                it("회원 가입 토큰을 응답한다 : ${socialLoginProvider.name}") {
                    // arrange
                    val idToken = "idToken"
                    val command = UserSocialLoginUseCase.Command(
                        socialLoginProvider = socialLoginProvider,
                        idToken = idToken,
                    )

                    // act
                    val result: UserSocialLoginUseCase.Result = sut.invoke(command)

                    // assert
                    result.shouldBeTypeOf<UserSocialLoginUseCase.Result.NotRegistered>()
                    result.registerToken.shouldBeTypeOf<String>()
                }
            }

        }

    }

})
