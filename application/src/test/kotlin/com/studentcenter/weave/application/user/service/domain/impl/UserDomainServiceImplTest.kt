package com.studentcenter.weave.application.user.service.domain.impl

import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("UserDomainServiceImpl")
class UserDomainServiceImplTest : DescribeSpec({

    val userRepositorySpy = UserRepositorySpy()
    val sut = UserDomainServiceImpl(userRepositorySpy)

    afterTest { userRepositorySpy.clear() }

    describe("create") {

        it("유저를 생성하고 저장한다") {
            // arrange
            val userInfo: User = UserFixtureFactory.create()

            // act
            val createdUser: User = sut.create(
                nickname = userInfo.nickname,
                email = userInfo.email,
                gender = userInfo.gender,
                mbti = userInfo.mbti,
                birthYear = userInfo.birthYear,
                universityId = userInfo.universityId,
                majorId = userInfo.majorId,
                avatar = userInfo.avatar,
            )

            // assert
            userRepositorySpy.findById(createdUser.id) shouldBe createdUser
        }
    }
})
