package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.UniversityVerificationException
import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.inbound.VerifyUniversityVerificationNumber
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserSilRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserUniversityVerificationInfoRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserVerificationNumberRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.VerificationNumberMailer
import com.studentcenter.weave.application.user.service.domain.impl.UserSilDomainServiceImpl
import com.studentcenter.weave.application.user.service.domain.impl.UserUniversityVerificationInfoDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.application.user.vo.UserUniversityVerificationNumber
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfoFixtureFactory
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import java.util.*

@DisplayName("VerifyUniversityVerificationNumberTest")
class VerifyUniversityVerificationNumberTest : DescribeSpec({

    val userRepository = UserRepositorySpy()
    val userSilRepository = UserSilRepositorySpy()
    val userUniversityVerificationInfoRepository = UserUniversityVerificationInfoRepositorySpy()
    val userVerificationNumberRepository = UserVerificationNumberRepositorySpy()

    val userSilDomainService = UserSilDomainServiceImpl(userSilRepository)
    val userVerificationInfoDomainService = UserUniversityVerificationInfoDomainServiceImpl(
        userUniversityVerificationInfoRepository
    )
    val verificationNumberMailer = mockk<VerificationNumberMailer>(relaxed = true)
    val sendVerificationNumberEmailService =
        SendVerificationEmailService(
            verificationNumberMailer = verificationNumberMailer,
            verificationInfoDomainService = userVerificationInfoDomainService,
            userVerificationNumberRepository = userVerificationNumberRepository,
        )
    val sut = VerifyUniversityVerificationNumberService(
        userSilDomainService = userSilDomainService,
        userVerificationInfoDomainService = userVerificationInfoDomainService,
        verificationNumberRepository = userVerificationNumberRepository,
        userRepository = userRepository,
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
                sendVerificationNumberEmailService.invoke(Email("weave@studentcenter.com"))
                val verificationNumber =
                    userVerificationNumberRepository.findByUserId(userFixture.id)!!.second
                val email = Email("invalid@studentcenter.com")
                val command = VerifyUniversityVerificationNumber.Command(email, verificationNumber)

                // act, assert
                shouldThrow<UniversityVerificationException.InvalidVerificationInformation> {
                    sut.invoke(
                        command
                    )
                }
            }
        }

        context("요청된 인증 코드가 저장된 인증 코드 다른 경우") {
            it("예외를 던진다.") {
                // arrange
                val userFixture = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                val email = Email("weave@studentcenter.com")
                sendVerificationNumberEmailService.invoke(email)
                val verificationNumber =
                    userVerificationNumberRepository.findByUserId(userFixture.id)!!.second
                val invalidVerificationNumber = UserUniversityVerificationNumber(
                    verificationNumber.value.replace(
                        verificationNumber.value[0],
                        if (verificationNumber.value[0] == '9') '8' else verificationNumber.value[0] + 1
                    )
                )
                val command =
                    VerifyUniversityVerificationNumber.Command(email, invalidVerificationNumber)

                // act, assert
                shouldThrow<UniversityVerificationException.InvalidVerificationInformation> {
                    sut.invoke(
                        command
                    )
                }
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
                val command = VerifyUniversityVerificationNumber.Command(email, verificationNumber)

                // act, assert
                shouldThrow<UniversityVerificationException.VerificationInformationNotFound> {
                    sut.invoke(
                        command
                    )
                }
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
                val command = VerifyUniversityVerificationNumber.Command(email, verificationNumber)

                // act, assert
                shouldThrow<UniversityVerificationException.AlreadyVerifiedUser> {
                    sut.invoke(
                        command
                    )
                }
            }
        }

        context("인증 번호와 이메일이 모두 일치하고, 사전등록되지 않은 유저면") {
            it("유저의 이메일 인증 여부를 true로 변경하고, 30실을 지급한다.") {
                // arrange
                val user = UserFixtureFactory
                    .create()
                    .also { userRepository.save(it) }

                userSilDomainService.create(user.id)
                val userAuthentication = UserAuthenticationFixtureFactory.create(user)

                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                val email = Email("weave@studentcenter.com")
                sendVerificationNumberEmailService.invoke(email)
                val verificationNumber =
                    userVerificationNumberRepository.findByUserId(user.id)!!.second
                val command = VerifyUniversityVerificationNumber.Command(email, verificationNumber)

                // act
                sut.invoke(command)

                // assert
                userRepository.getById(user.id).isUnivVerified shouldBeEqual true
                userVerificationNumberRepository.findByUserId(user.id) shouldBe null
                userSilRepository.getByUserId(user.id).amount shouldBe 30
            }
        }

        context("인증 번호와 이메일이 모두 일치하고, 사전등록된 유저면") {
            it("유저의 이메일 인증 여부를 true로 변경하고, 60실을 지급한다.") {
                // arrange
                val user = UserFixtureFactory
                    .create()
                    .also { userRepository.save(it) }

                userSilDomainService.create(user.id)
                val userAuthentication = UserAuthenticationFixtureFactory.create(user)

                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                val email = Email("weave@studentcenter.com")

                userRepository.addPreRegisterEmail(email)

                sendVerificationNumberEmailService.invoke(email)
                val verificationNumber =
                    userVerificationNumberRepository.findByUserId(user.id)!!.second
                val command = VerifyUniversityVerificationNumber.Command(email, verificationNumber)

                // act
                sut.invoke(command)

                // assert
                userRepository.getById(user.id).isUnivVerified shouldBeEqual true
                userVerificationNumberRepository.findByUserId(user.id) shouldBe null
                userSilRepository.getByUserId(user.id).amount shouldBe 60
            }
        }
    }

})
