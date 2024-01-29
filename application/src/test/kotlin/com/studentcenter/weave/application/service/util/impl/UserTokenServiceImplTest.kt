package com.studentcenter.weave.application.service.util.impl

import com.studentcenter.weave.application.common.properties.JwtTokenPropertiesFixtureFactory
import com.studentcenter.weave.application.service.util.impl.strategy.OpenIdTokenResolveStrategyFactory
import com.studentcenter.weave.application.service.util.impl.strategy.OpenIdTokenResolveStrategyStub
import com.studentcenter.weave.application.vo.UserTokenClaims
import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.entity.UserFixtureFactory
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.every
import io.mockk.mockk

@DisplayName("UserTokenServiceImpl")
class UserTokenServiceImplTest : DescribeSpec({


    val openIdTokenResolveStrategyFactoryMock: OpenIdTokenResolveStrategyFactory =
        mockk<OpenIdTokenResolveStrategyFactory>()
    val sut = UserTokenServiceImpl(
        jwtTokenProperties = JwtTokenPropertiesFixtureFactory.create(),
        openIdTokenResolveStrategyFactory = openIdTokenResolveStrategyFactoryMock
    )

    beforeTest {
        every { openIdTokenResolveStrategyFactoryMock.getStrategy(any()) } returns OpenIdTokenResolveStrategyStub()
    }

    describe("resolveIdToken") {

        it("idToken을 파싱하여 유저정보가 있는 IdToken Claim객체를 반환한다 ") {
            // arrange
            val idToken = "idToken"
            val user = UserFixtureFactory.create()
            val provider = SocialLoginProvider.APPLE

            // act
            val actual = sut.resolveIdToken(idToken, provider)

            // assert
            actual shouldBe UserTokenClaims.IdToken(
                nickname = user.nickname,
                email = user.email,
            )

        }

    }

    describe("generateRegisterToken") {

        it("회원가입 토큰을 생성한다") {
            // arrange
            val email = Email("test@test.com")
            val nickname = Nickname("test")
            val provider = SocialLoginProvider.APPLE

            // act
            val actual = sut.generateRegisterToken(email, nickname, provider)

            // assert
            actual.shouldBeInstanceOf<String>()

        }

    }

    describe("resolveRegisterToken") {

        it("회원가입 토큰을 파싱하여 유저정보가 있는 RegisterToken Claim객체를 반환한다") {
            // arrange
            val email = Email("test@test.com")
            val nickname = Nickname("test")
            val provider = SocialLoginProvider.APPLE
            val registerToken = sut.generateRegisterToken(email, nickname, provider)

            // act
            val actual = sut.resolveRegisterToken(registerToken)

            // assert
            actual shouldBe UserTokenClaims.RegisterToken(
                email = email,
                nickname = nickname,
                socialLoginProvider = provider,
            )
        }

    }

    describe("generateAccessToken") {

        it("유저정보를 토큰에 담아서 AccessToken을 생성한다") {
            // arrange
            val user: User = UserFixtureFactory.create()

            // act
            val actual: String = sut.generateAccessToken(user)

            // assert
            actual.shouldBeInstanceOf<String>()
        }

    }

    describe("resolveAccessToken") {

        it("AccessToken을 파싱하여 유저정보가 있는 AccessToken Claim객체를 반환한다") {
            // arrange
            val user: User = UserFixtureFactory.create()
            val accessToken: String = sut.generateAccessToken(user)

            // act
            val actual: UserTokenClaims.AccessToken = sut.resolveAccessToken(accessToken)

            // assert
            actual shouldBe UserTokenClaims.AccessToken(
                userId = user.id,
                nickname = user.nickname,
                email = user.email,
                avatar = user.avatar,
            )
        }

    }

    describe("generateRefreshToken") {

        it("유저정보를 토큰에 담아서 RefreshToken을 생성한다") {
            // arrange
            val user = UserFixtureFactory.create()

            // act
            val actual = sut.generateRefreshToken(user)

            // assert
            actual.shouldBeInstanceOf<String>()
        }

    }

    describe("resolveRefreshToken") {

        it("RefreshToken을 파싱하여 유저정보가 있는 RefreshToken Claim객체를 반환한다") {
            // arrange
            val user = UserFixtureFactory.create()
            val refreshToken = sut.generateRefreshToken(user)

            // act
            val actual = sut.resolveRefreshToken(refreshToken)

            // assert
            actual shouldBe UserTokenClaims.RefreshToken(
                userId = user.id,
            )
        }
    }

})
