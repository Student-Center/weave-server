package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.outbound.UserRefreshTokenRepositorySpy
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("LogoutApplicationService")
class LogoutApplicationServiceTest : DescribeSpec({

    val userRefreshTokenRepositorySpy = UserRefreshTokenRepositorySpy()
    val sut = LogoutApplicationService(userRefreshTokenRepositorySpy)

    afterTest {
        userRefreshTokenRepositorySpy.clear()
        SecurityContextHolder.clearContext()
    }

    describe("UserLogoutApplicationService") {
        it("저장된 갱신 토큰을 삭제한다") {
            // arrange
            val user: User = UserFixtureFactory.create()
            val userAuthentication = UserAuthenticationFixtureFactory.create(user)
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
