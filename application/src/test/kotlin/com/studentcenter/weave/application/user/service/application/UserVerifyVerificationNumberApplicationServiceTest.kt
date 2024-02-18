package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.inbound.UserVerifyVerificationNumberUseCase
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserSilRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserUniversityVerificationInfoRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserVerificationNumberRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.VerificationNumberMailer
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.service.domain.impl.UserSilDomainServiceImpl
import com.studentcenter.weave.application.user.service.domain.impl.UserUniversityVerificationInfoDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.application.user.vo.UserUniversityVerificationNumber
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfoFixtureFactory
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import java.util.*

@DisplayName("UserVerifyVerificationNumberApplicationService")
class UserVerifyVerificationNumberApplicationServiceTest : DescribeSpec({

    val userRepository = UserRepositorySpy()
    val userSilRepository = UserSilRepositorySpy()
    val userUniversityVerificationInfoRepository = UserUniversityVerificationInfoRepositorySpy()
    val userVerificationNumberRepository = UserVerificationNumberRepositorySpy()

    val userDomainService = UserDomainServiceImpl(userRepository)
    val userSilDomainService = UserSilDomainServiceImpl(userSilRepository)
    val userVerificationInfoDomainService = UserUniversityVerificationInfoDomainServiceImpl(
        userUniversityVerificationInfoRepository
    )
    val verificationNumberMailer = mockk<VerificationNumberMailer>(relaxed = true)
    val userSendVerificationNumberEmailApplicationService =
        UserSendVerificationNumberEmailApplicationService(
            verificationNumberMailer = verificationNumberMailer,
            verificationInfoDomainService = userVerificationInfoDomainService,
            userVerificationNumberRepository = userVerificationNumberRepository,
        )
    val sut = UserVerifyVerificationNumberApplicationService(
        userDomainService = userDomainService,
        userSilDomainService = userSilDomainService,
        userVerificationInfoDomainService = userVerificationInfoDomainService,
        verificationNumberRepository = userVerificationNumberRepository,
    )

    afterEach {
        userRepository.clear()
        userSilRepository.clear()
        userUniversityVerificationInfoRepository.clear()
        userVerificationNumberRepository.clear()
        SecurityContextHolder.clearContext()
    }

    describe("대학 인증 요청 유스케이스") {
        context("요청된 이메일이 저장된 이메일과 다른 경우") {
            it("예외를 던진다.") {
                // arrange
                val userFixture = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                userSendVerificationNumberEmailApplicationService.invoke(Email("weave@studentcenter.com"))
                val verificationNumber =
                    userVerificationNumberRepository.findByUserId(userFixture.id)!!.second
                val email = Email("invalid@studentcenter.com")
                val command = UserVerifyVerificationNumberUseCase.Command(email, verificationNumber)

                // act, assert
                shouldThrow<RuntimeException> { sut.invoke(command) }
            }
        }

        context("요청된 인증 코드가 저장된 인증 코드 다른 경우") {
            it("예외를 던진다.") {
                // arrange
                val userFixture = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                val email = Email("weave@studentcenter.com")
                userSendVerificationNumberEmailApplicationService.invoke(email)
                val verificationNumber =
                    userVerificationNumberRepository.findByUserId(userFixture.id)!!.second
                val invalidVerificationNumber = UserUniversityVerificationNumber(
                    verificationNumber.value.replace(
                        verificationNumber.value[0],
                        if (verificationNumber.value[0] == '9') '8' else verificationNumber.value[0] + 1
                    )
                )
                val command =
                    UserVerifyVerificationNumberUseCase.Command(email, invalidVerificationNumber)

                // act, assert
                shouldThrow<CustomException> { sut.invoke(command) }
            }
        }

        context("요청된 유저에 대한 인증 정보가 없는 경우") {
            it("예외를 던진다.") {
                // arrange
                val userFixture = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                val email = Email("weave@studentcenter.com")
                val verificationNumber = UserUniversityVerificationNumber.generate()
                val command = UserVerifyVerificationNumberUseCase.Command(email, verificationNumber)

                // act, assert
                shouldThrow<CustomException> { sut.invoke(command) }
            }
        }

        context("이미 인증된 유저인 경우") {
            it("예외를 던진다.") {
                // arrange
                val userFixture = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                val verificationInfo = UserUniversityVerificationInfoFixtureFactory.create(
                    user = userFixture
                )
                userVerificationInfoDomainService.save(verificationInfo)
                val email = Email("weave@studentcenter.com")
                val verificationNumber = UserUniversityVerificationNumber.generate()
                val command = UserVerifyVerificationNumberUseCase.Command(email, verificationNumber)

                // act, assert
                shouldThrow<CustomException> { sut.invoke(command) }
            }
        }

        context("인증 번호와 이메일이 모두 일치하는 경우") {
            it("인증 처리를 한다.") {
                // arrange
                val userFixture = UserFixtureFactory.create()
                val user = userDomainService.create(
                    nickname = userFixture.nickname,
                    email = userFixture.email,
                    gender = userFixture.gender,
                    mbti = userFixture.mbti,
                    birthYear = userFixture.birthYear,
                    universityId = userFixture.universityId,
                    majorId = userFixture.majorId,
                    avatar = userFixture.avatar,
                )
                userSilDomainService.create(user.id)
                val userAuthentication = UserAuthentication(
                    userId = user.id,
                    nickname = user.nickname,
                    email = user.email,
                    avatar = user.avatar,
                    gender = user.gender
                )
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                val email = Email("weave@studentcenter.com")
                userSendVerificationNumberEmailApplicationService.invoke(email)
                val verificationNumber =
                    userVerificationNumberRepository.findByUserId(user.id)!!.second
                val command = UserVerifyVerificationNumberUseCase.Command(email, verificationNumber)

                // act
                sut.invoke(command)

                // assert
                userRepository.getById(user.id).isUnivVerified shouldBeEqual true
                userVerificationNumberRepository.findByUserId(user.id) shouldBe null
                userSilRepository.getByUserId(user.id).amount shouldBeGreaterThan 0
            }
        }
    }

})
