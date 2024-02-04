package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.inbound.UserGetMyProfileUseCase
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("UserGetMyProfileApplicationService")
class UserGetMyProfileApplicationServiceTest : DescribeSpec({

    val userRepositorySpy = UserRepositorySpy()
    val userDomainService = UserDomainServiceImpl(userRepositorySpy)
    val sut = UserGetMyProfileApplicationService(userDomainService)

    afterEach {
        SecurityContextHolder.clearContext()
    }

    describe("UserGetMyProfileApplicationService") {
        context("사용자가 로그인상태일때") {
            it("사용자의 프로필 정보를 응답한다.") {
                // arrange
                val user: User = UserFixtureFactory.create()
                val userAuthentication = UserAuthentication(
                    userId = user.id,
                    nickname = user.nickname,
                    email = user.email,
                    avatar = user.avatar,
                )
                val userSecurityContext = UserSecurityContext(userAuthentication)
                SecurityContextHolder.setContext(userSecurityContext)
                userRepositorySpy.save(user)

                // act
                val result: UserGetMyProfileUseCase.Result = sut.invoke()

                // assert
                result.id shouldBe user.id
                result.nickname shouldBe user.nickname
                result.birthYear shouldBe user.birthYear
                result.avatar shouldBe user.avatar
                result.mbti shouldBe user.mbti
                result.animalType shouldBe user.animalType
                result.height shouldBe user.height
            }
        }
    }

})
