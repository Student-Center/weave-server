package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.outbound.UserUniversityVerificationInfoRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserVerificationNumberRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.VerificationNumberMailer
import com.studentcenter.weave.application.user.service.domain.impl.UserUniversityVerificationInfoDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfoFixtureFactory
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.shouldNotBe
import io.mockk.mockk
import io.mockk.verify
import java.util.*

@DisplayName("SendVerificationEmailApplicationService")
class SendVerificationEmailApplicationServiceTest : DescribeSpec({

    val verificationNumberMailer = mockk<VerificationNumberMailer>(relaxed = true)
    val userVerificationNumberRepository = UserVerificationNumberRepositorySpy()
    val userUniversityVerificationInfoRepository = UserUniversityVerificationInfoRepositorySpy()
    val userVerificationInfoDomainService = UserUniversityVerificationInfoDomainServiceImpl(
        userUniversityVerificationInfoRepository
    )
    val sut = SendVerificationEmailApplicationService(
        verificationNumberMailer = verificationNumberMailer,
        userVerificationNumberRepository = userVerificationNumberRepository,
        verificationInfoDomainService = userVerificationInfoDomainService,
    )

    afterEach {
        userVerificationNumberRepository.clear()
        SecurityContextHolder.clearContext()
    }

    describe("사용자 인증 번호 전송 유스케이스") {
        context("사용자가 최초 인증 시점에") {
            it("인증 번호 전송 요청을 하면 인증 번호 전송, 저장한다.") {
                // arrange
                val userFixture = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                val email = Email("weave@studentcenter.com")

                // act
                sut.invoke(email)

                // assert
                userVerificationNumberRepository.findByUserId(userFixture.id) shouldNotBe null
                val verificationNumber = userVerificationNumberRepository
                    .findByUserId(userFixture.id)!!
                    .second
                verify { verificationNumberMailer.send(email, verificationNumber, any()) }
            }
        }

        context("이미 인증 번호를 전송했더라도") {
            it("전송 요청을 하면 인증 번호 전송 및 저장을 한다.") {
                // arrange
                val userFixture = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                sut.invoke(Email("weave@studentcenter.com"))
                val (existEmail, existVerificationNumber) = userVerificationNumberRepository
                    .findByUserId(userFixture.id)!!

                val email = Email("re-weave@studentcenter.com")
                // act
                sut.invoke(email)

                // assert
                val (currentEmail, currentVerificationNumber) = userVerificationNumberRepository
                    .findByUserId(userFixture.id)!!
                currentEmail shouldNotBeEqual existEmail
                currentVerificationNumber shouldNotBeEqual existVerificationNumber
                verify {
                    verificationNumberMailer.send(currentEmail, currentVerificationNumber, any())
                }
            }
        }

        context("이미 인증된 메일이라면") {
            it("예외를 던진다") {
                // arrange
                val userFixture = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                val email = Email("re-weave@studentcenter.com")
                val verificationInfo = UserUniversityVerificationInfoFixtureFactory.create(
                    user = userFixture,
                    universityEmail = email,
                )
                userVerificationInfoDomainService.save(verificationInfo)

                // act, assert
                shouldThrow<CustomException> { sut.invoke(email) }
            }
        }
    }

})
