package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.outbound.UserRefreshTokenRepositorySpy
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("UserLogoutApplicationService")
class UserLogoutApplicationServiceTest : DescribeSpec({

    val userRefreshTokenRepositorySpy = UserRefreshTokenRepositorySpy()
    val sut = UserLogoutApplicationService(userRefreshTokenRepositorySpy)

    afterTest {
        userRefreshTokenRepositorySpy.clear()
        SecurityContextHolder.clearContext()
    }

    describe("UserLogoutApplicationService") {
        it("저장된 갱신 토큰을 삭제한다") {
            // arrange
            val user: User = UserFixtureFactory.create()
            val userAuthentication = UserAuthentication(
                userId = user.id,
                email = user.email,
                nickname = user.nickname,
                avatar = user.avatar
            )
            userRefreshTokenRepositorySpy.save(
                userId = user.id,
                refreshToken = "refreshToken",
                expirationSeconds = 1000
            )
            SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

            // act
            sut.invoke()

            // assert
            userRefreshTokenRepositorySpy.findByUserId(user.id) shouldBe null
        }
    }

})
