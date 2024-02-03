package com.studentcenter.weave.domain.user.entity

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DeletedUserInfoTest : FunSpec({

    test("탈퇴 유저 정보 생성") {
        // arrange
        val user = UserFixtureFactory.create()
        val userAuthInfo = UserAuthInfoFixtureFactory.create()
        val reason = "탈퇴 사유"

        // act
        val deletedUserInfo = DeletedUserInfo.create(
            userAuthInfo = userAuthInfo,
            reason = reason,
        )

        // assert
        deletedUserInfo.email shouldBe user.email
        deletedUserInfo.socialLoginProvider shouldBe userAuthInfo.socialLoginProvider
        deletedUserInfo.reason shouldBe reason
    }

    test("탈퇴 사유가 100자 초과인 경우 생성 실패") {
        // arrange
        val userAuthInfo = UserAuthInfoFixtureFactory.create()
        val reason = "탈퇴 사유".repeat(101)

        // act, assert
        shouldThrow<IllegalArgumentException> { DeletedUserInfo.create(userAuthInfo, reason) }
    }

    test("탈퇴 사유가 공백인 경우 생성 성공") {
        // arrange
        val userAuthInfo = UserAuthInfoFixtureFactory.create()

        // act
        shouldNotThrowAny { DeletedUserInfo.create(userAuthInfo) }
    }

})
