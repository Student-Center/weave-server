package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class UserSilTest : FunSpec({


    test("유저 실 생성") {
        val userId = UuidCreator.create()
        val userSil = UserSil.create(userId)

        userSil.userId shouldBe userId
        userSil.amount shouldBe 0
    }

    test("0 이하의 실을 생성할 수 없다") {
        val userId = UuidCreator.create()

        shouldThrow<IllegalArgumentException> {
            UserSil(userId = userId, amount = -1)
        }
    }

})
