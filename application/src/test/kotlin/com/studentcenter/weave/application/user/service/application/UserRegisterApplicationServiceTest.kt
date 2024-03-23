package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.properties.JwtTokenProperties
import com.studentcenter.weave.application.common.properties.JwtTokenPropertiesFixtureFactory
import com.studentcenter.weave.application.user.port.inbound.UserRegisterUseCase
import com.studentcenter.weave.application.user.port.outbound.UserAuthInfoRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserEventPortStub
import com.studentcenter.weave.application.user.port.outbound.UserRefreshTokenRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserSilRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.UserAuthInfoDomainServiceImpl
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.service.domain.impl.UserSilDomainServiceImpl
import com.studentcenter.weave.application.user.service.util.impl.UserTokenServiceImpl
import com.studentcenter.weave.application.user.service.util.impl.strategy.OpenIdTokenResolveStrategyFactoryStub
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockk.clearAllMocks
import java.util.*

@DisplayName("UserRegisterApplicationService")
class UserRegisterApplicationServiceTest : DescribeSpec({

    val userRepositorySpy = UserRepositorySpy()
    val userAuthInfoRepositorySpy = UserAuthInfoRepositorySpy()
    val userSilRepositorySpy = UserSilRepositorySpy()
    val jwtTokenProperties: JwtTokenProperties = JwtTokenPropertiesFixtureFactory.create()

    val userEventPortStub: UserEventPortStub = object : UserEventPortStub() {
        override fun sendRegistrationMessage(
            user: User,
            userCount: Int,
        ) {
            throw RuntimeException("이벤트 에러 발생")
        }
    }

    val sut = UserRegisterApplicationService(
        userTokenService = UserTokenServiceImpl(
            jwtTokenProperties = jwtTokenProperties,
            userRefreshTokenRepository = UserRefreshTokenRepositorySpy(),
            openIdTokenResolveStrategyFactory = OpenIdTokenResolveStrategyFactoryStub(),
        ),
        userDomainService = UserDomainServiceImpl(userRepositorySpy),
        userAuthInfoDomainService = UserAuthInfoDomainServiceImpl(userAuthInfoRepositorySpy),
        userSilDomainService = UserSilDomainServiceImpl(userSilRepositorySpy),
        userEventPort = userEventPortStub,
    )

    afterEach {
        userRepositorySpy.clear()
        userAuthInfoRepositorySpy.clear()
        userSilRepositorySpy.clear()
        SecurityContextHolder.clearContext()
        clearAllMocks()
    }

    describe("회원가입 유스케이스") {
        context("요청이 유효하면") {
            // arrange
            val user: User = UserFixtureFactory.create()
            val socialLoginProvider = SocialLoginProvider.KAKAO
            val command = UserRegisterUseCase.Command(
                nickname = user.nickname,
                email = user.email,
                socialLoginProvider = socialLoginProvider,
                gender = user.gender,
                mbti = user.mbti,
                birthYear = user.birthYear,
                universityId = user.universityId,
                majorId = user.majorId
            )

            // act
            val result: UserRegisterUseCase.Result = sut.invoke(command)

            it("로그인 토큰을 반환한다") {
                // assert
                result.shouldBeTypeOf<UserRegisterUseCase.Result.Success>()
                result.accessToken.shouldBeTypeOf<String>()
                result.refreshToken.shouldBeTypeOf<String>()
            }
        }

        context("이벤트 발행 과정에서 에러가 발생하여도") {
            it("회원가입은 정상적으로 진행되어야 한다.") {
                // arrange
                val user: User = UserFixtureFactory.create()
                val socialLoginProvider = SocialLoginProvider.KAKAO
                val command = UserRegisterUseCase.Command(
                    nickname = user.nickname,
                    email = user.email,
                    socialLoginProvider = socialLoginProvider,
                    gender = user.gender,
                    mbti = user.mbti,
                    birthYear = user.birthYear,
                    universityId = user.universityId,
                    majorId = user.majorId
                )

                // act
                val result: UserRegisterUseCase.Result = sut.invoke(command)

                // assert
                result.shouldBeTypeOf<UserRegisterUseCase.Result.Success>()
                result.accessToken.shouldBeTypeOf<String>()
                result.refreshToken.shouldBeTypeOf<String>()
            }
        }

    }

})
