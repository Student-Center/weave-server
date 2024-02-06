package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("UserSetMyHeightApplicationService")
class UserSetMyHeightApplicationServiceTest : DescribeSpec({

    val userRepositorySpy = UserRepositorySpy()
    val userDomainService = UserDomainServiceImpl(userRepositorySpy)
    val sut = UserSetMyHeightApplicationService(userDomainService)

    afterTest {
        userRepositorySpy.clear()
        SecurityContextHolder.clearContext()
    }

    describe("유저 키 설정 유즈케이스") {
        context("유저가 로그인 되어있으면") {
            it("로그인된 유저의 키를 업데이트 한다") {
                // arrange
                val height = Height(180)
                val userFixture = UserFixtureFactory.create()
                userRepositorySpy.save(userFixture)

                val userAuthentication = UserAuthentication(
                    userId = userFixture.id,
                    nickname = userFixture.nickname,
                    email = userFixture.email,
                    avatar = userFixture.avatar,
                )
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act
                sut.invoke(height)

                // assert
                val user: User = userRepositorySpy.getById(userFixture.id)
                user.height shouldBe height
            }
        }
    }

})
