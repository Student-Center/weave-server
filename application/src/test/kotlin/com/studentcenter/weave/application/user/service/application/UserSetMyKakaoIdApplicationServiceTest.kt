package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserSilRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.service.domain.impl.UserSilDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserSil
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import com.studentcetner.weave.support.lock.DistributedLockTestInitializer
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearStaticMockk

@DisplayName("UserSetMyKakaoIdApplicationServiceTest")
class UserSetMyKakaoIdApplicationServiceTest : DescribeSpec({

    val userRepository = UserRepositorySpy()
    val userDomainService = UserDomainServiceImpl(userRepository)

    val userSilRepositorySpy = UserSilRepositorySpy()
    val userSilDomainService = UserSilDomainServiceImpl(userSilRepositorySpy)
    val sut = UserSetMyKakaoIdApplicationService(
        userDomainService = userDomainService,
        userSilDomainService = userSilDomainService
    )

    beforeTest {
        DistributedLockTestInitializer.mockExecutionByStatic()
    }

    afterTest {
        userRepository.clear()
        userSilRepositorySpy.clear()
        SecurityContextHolder.clearContext()
        clearStaticMockk()
    }

    describe("UserSetMyKakaoIdApplicationService") {
        context("이미 등록된 카카오 아이디가 있는 경우") {
            it("예외를 발생시킨다.") {
                // arrange
                val kakaoId = "kakaoId"
                UserFixtureFactory
                    .create(kakaoId = kakaoId)
                    .also { userRepository.save(it) }

                val user: User = UserFixtureFactory.create()
                val userAuthentication: UserAuthentication =
                    UserAuthenticationFixtureFactory.create(user)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))
                UserSil
                    .create(user.id)
                    .also { userSilRepositorySpy.save(it) }

                // act, assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(kakaoId)
                }
            }
        }

        context("이미 카카오 아이디를 등록한 경우") {
            it("예외를 발생시킨다.") {
                // arrange
                val kakaoId = "kakaoId"
                val user: User = UserFixtureFactory
                    .create(kakaoId = kakaoId)
                    .also { userRepository.save(it) }

                val userAuthentication: UserAuthentication =
                    UserAuthenticationFixtureFactory.create(user)

                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                UserSil
                    .create(user.id)
                    .also { userSilRepositorySpy.save(it) }

                // act, assert
                shouldThrow<IllegalArgumentException> {
                    sut.invoke(kakaoId)
                }
            }
        }

        context("정상적으로 카카오 아이디를 등록한 경우") {
            it("카카오 아이디가 등록되고, 사용자의 SIL이 30 증가한다.") {
                // arrange
                val user: User = UserFixtureFactory
                    .create()
                    .also { userRepository.save(it) }
                val kakaoId = "kakaoId"

                val userAuthentication: UserAuthentication =
                    UserAuthenticationFixtureFactory.create(user)

                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                UserSil
                    .create(user.id)
                    .also { userSilRepositorySpy.save(it) }

                // act
                sut.invoke(kakaoId)

                // assert
                val userKakaoAuth = userDomainService.getById(user.id)
                userKakaoAuth.kakaoId shouldBe kakaoId

                val userSil = userSilDomainService.getByUserId(user.id)
                userSil.amount shouldBe 30
            }
        }
    }

})
