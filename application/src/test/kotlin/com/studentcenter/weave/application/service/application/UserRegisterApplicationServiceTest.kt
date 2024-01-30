package com.studentcenter.weave.application.service.application

import com.studentcenter.weave.application.port.inbound.UserRegisterUseCase
import com.studentcenter.weave.application.port.outbound.UserAuthInfoRepositorySpy
import com.studentcenter.weave.application.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.service.domain.impl.UserAuthInfoDomainServiceImpl
import com.studentcenter.weave.application.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.service.util.UserTokenServiceStub
import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.entity.UserFixtureFactory
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeTypeOf

@DisplayName("UserRegisterApplicationService")
class UserRegisterApplicationServiceTest : DescribeSpec({

    describe("회원가입 유스케이스") {

        val userRepositorySpy = UserRepositorySpy()
        val userAuthInfoRepositorySpy = UserAuthInfoRepositorySpy()
        val sut = UserRegisterApplicationService(
            userTokenService = UserTokenServiceStub(),
            userDomainService = UserDomainServiceImpl(userRepositorySpy),
            userAuthInfoDomainService = UserAuthInfoDomainServiceImpl(userAuthInfoRepositorySpy)
        )

        context("회원가입 요청이 유효하면") {

            it("해당회원을 생성하고 로그인 토큰을 반환한다") {
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
