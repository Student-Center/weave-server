package com.studentcenter.weave.domain.user.entity

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

})
