package com.studentcenter.weave.domain.entity

import com.studentcenter.weave.domain.enum.SocialLoginProvider
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DeactivateUserInfoTest : FunSpec({

    test("탈퇴 유저 정보 생성") {
        // arrange
        val user = UserFixtureFactory.create()
        val userAuthInfo = UserAuthInfoFixtureFactory.create()
        val reason = "탈퇴 사유"

        // act
        val deactivateUserInfo = DeactivateUserInfo.create(
            userAuthInfo = userAuthInfo,
            reason = reason,
        )

        // assert
        deactivateUserInfo.email shouldBe user.email
        deactivateUserInfo.socialLoginProvider shouldBe userAuthInfo.socialLoginProvider
        deactivateUserInfo.reason shouldBe reason
    }

})
