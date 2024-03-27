package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("ModifyMyMbtiApplicationService")
class ModifyMyMbtiApplicationServiceTest : DescribeSpec({

    val userRepositorySpy = UserRepositorySpy()
    val userDomainService = UserDomainServiceImpl(userRepositorySpy)
    val sut = ModifyMyMbtiApplicationService(userDomainService)

    describe("유저 MBTI 수정 유스케이스") {
        context("로그인 되어 있으면") {
            it("로그인 된 유저의 MBTI를 수정한다") {
                // arrange
                val mbti = "EnFP"
                val user = UserFixtureFactory.create()
                userRepositorySpy.save(user)

                val userAuthentication = UserAuthenticationFixtureFactory.create(user)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act
                sut.invoke(Mbti(mbti))

                // assert
                val updatedUser = userRepositorySpy.getById(user.id)
                updatedUser.mbti shouldBe Mbti(mbti)
            }
        }

    }


})
