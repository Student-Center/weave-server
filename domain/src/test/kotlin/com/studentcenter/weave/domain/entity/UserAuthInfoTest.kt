package com.studentcenter.weave.domain.entity

import com.studentcenter.weave.domain.enum.SocialLoginProvider
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class UserAuthInfoTest : FunSpec({

    test("유저 인증 정보 생성") {
        // arrange
        val user = UserFixtureFactory.create()
        val socialLoginProvider = SocialLoginProvider.KAKAO

        // act
        val userAuthInfo = UserAuthInfo.create(
            user = user,
            socialLoginProvider = socialLoginProvider,
        )

        // assert
        userAuthInfo.userId shouldBe user.id
        userAuthInfo.socialLoginProvider shouldBe socialLoginProvider
    }

})
