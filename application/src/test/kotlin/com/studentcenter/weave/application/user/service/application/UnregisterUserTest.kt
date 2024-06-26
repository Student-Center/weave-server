package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.AuthException
import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.inbound.UnregisterUser
import com.studentcenter.weave.application.user.port.outbound.DeletedUserInfoRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserAuthInfoRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.DeletedUserInfoServiceImpl
import com.studentcenter.weave.application.user.service.domain.impl.UserAuthInfoDomainServiceImpl
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserAuthInfo
import com.studentcenter.weave.domain.user.entity.UserAuthInfoFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

@DisplayName("UnregisterUserTest")
class UnregisterUserTest : DescribeSpec({

    val userRepositorySpy = UserRepositorySpy()
    val userAuthInfoRepositorySpy = UserAuthInfoRepositorySpy()
    val deletedUserInfoRepositorySpy = DeletedUserInfoRepositorySpy()

    val userDomainService = UserDomainServiceImpl(userRepositorySpy)
    val userAuthInfoDomainService = UserAuthInfoDomainServiceImpl(userAuthInfoRepositorySpy)
    val deletedUserInfoDomainService = DeletedUserInfoServiceImpl(deletedUserInfoRepositorySpy)

    val sut = UnregisterUserService(
        userDomainService = userDomainService,
        userAuthInfoDomainService = userAuthInfoDomainService,
        deletedUserInfoDomainService = deletedUserInfoDomainService,
    )

    afterEach {
        userRepositorySpy.clear()
        userAuthInfoRepositorySpy.clear()
        deletedUserInfoRepositorySpy.clear()
        SecurityContextHolder.clearContext()
    }

    describe("UserUnregisterService") {
        context("사용자가 로그인 되어있으면") {
            it("현재 로그인한 사용자의 사용자 정보와 인증정보를 삭제하고, 탈퇴 사용자 정보를 저장한다.") {
                // arrange
                val user: User = UserFixtureFactory.create()
                val userAuth: UserAuthInfo = UserAuthInfoFixtureFactory.create(user)
                userRepositorySpy.save(user)
                userAuthInfoRepositorySpy.save(userAuth)

                val loginAuthentication = UserAuthenticationFixtureFactory.create(user)
                SecurityContextHolder.setContext(UserSecurityContext(loginAuthentication))

                val command = UnregisterUser.Command()

                // act
                sut.invoke(command)

                // assert
                userRepositorySpy.findById(user.id) shouldBe null
                userAuthInfoRepositorySpy.findByEmail(user.email) shouldBe null
                deletedUserInfoRepositorySpy.findByEmailAndSocialLoginProvider(
                    user.email,
                    userAuth.socialLoginProvider
                ) shouldNotBe null
            }
        }

        context("로그인 되어있지 않으면") {
            it("인증되지 않은 사용자 예외를 발생시킨다.") {
                // arrange
                val command = UnregisterUser.Command()

                // act & assert
                shouldThrow<AuthException.UserNotAuthenticated> {
                    sut.invoke(command)
                }
            }
        }
    }

})
