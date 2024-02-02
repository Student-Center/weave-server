package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.AuthExceptionType
import com.studentcenter.weave.application.common.properties.JwtTokenPropertiesFixtureFactory
import com.studentcenter.weave.application.user.port.inbound.UserRefreshTokenUseCase
import com.studentcenter.weave.application.user.port.outbound.UserRefreshTokenRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.service.util.impl.UserTokenServiceImpl
import com.studentcenter.weave.application.user.service.util.impl.strategy.OpenIdTokenResolveStrategyFactoryStub
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.common.exception.CustomException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.util.*

@DisplayName("UserRefreshTokenApplicationService")
class UserRefreshTokenApplicationServiceTest : DescribeSpec({

    val userRepositorySpy = UserRepositorySpy()
    val userRefreshTokenRepositorySpy = UserRefreshTokenRepositorySpy()
    val userDomainServiceImpl = UserDomainServiceImpl(userRepositorySpy)
    val userTokenServiceImpl = UserTokenServiceImpl(
        jwtTokenProperties = JwtTokenPropertiesFixtureFactory.create(),
        userRefreshTokenRepository = userRefreshTokenRepositorySpy,
        openIdTokenResolveStrategyFactory = OpenIdTokenResolveStrategyFactoryStub(),
    )
    val sut = UserRefreshTokenApplicationService(
        userDomainService = userDomainServiceImpl,
        userTokenService = userTokenServiceImpl,
        userRefreshTokenRepository = userRefreshTokenRepositorySpy,
    )

    afterEach {
        userRepositorySpy.clear()
        userRefreshTokenRepositorySpy.clear()
    }

    describe("유저 토큰 갱신 유스케이스") {
        context("갱신 토큰이 만료되지 않았으면") {
            it("새로운 로그인 토큰(access, refresh)을 발급한다") {
                // arrange
                val userFixture: User = UserFixtureFactory.create()
                val refreshToken: String = userTokenServiceImpl.generateRefreshToken(userFixture)
                userRepositorySpy.save(userFixture)
                userRefreshTokenRepositorySpy.save(
                    userId = userFixture.id,
                    refreshToken = refreshToken,
                    expirationSeconds = JwtTokenPropertiesFixtureFactory.create().refresh.expireSeconds
                )
                val command = UserRefreshTokenUseCase.Command(refreshToken)

                // act
                val result: UserRefreshTokenUseCase.Result = sut.invoke(command)

                // assert
                result.refreshToken.shouldBeInstanceOf<String>()
                result.accessToken.shouldBeInstanceOf<String>()
            }
        }
        context("갱신 토큰이 존재하지 않으면") {
            it("갱신 토큰이 존재하지 않는다는 예외를 던진다") {
                // arrange
                val userFixture: User = UserFixtureFactory.create()
                val refreshToken: String = userTokenServiceImpl.generateRefreshToken(userFixture)
                userRepositorySpy.save(userFixture)
                userRefreshTokenRepositorySpy.clear()
                val command = UserRefreshTokenUseCase.Command(refreshToken)

                // act
                val exception: CustomException = shouldThrow { sut.invoke(command) }

                // assert
                exception.type shouldBe AuthExceptionType.REFRESH_TOKEN_NOT_FOUND
            }
        }
    }

})
