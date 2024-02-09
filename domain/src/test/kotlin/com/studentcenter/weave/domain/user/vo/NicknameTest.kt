package com.studentcenter.weave.domain.user.vo

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class NicknameTest : FunSpec ({

    test("닉네임이 존재하는 경우, 최대 길이는 10자 이하여야 합니다.") {

        val exception = shouldThrow<IllegalArgumentException> {
            Nickname("닉".repeat(11))
        }

        exception.message shouldBe "닉네임은 ${Nickname.MAX_LENGTH}자 이하여야 합니다."
    }

    test("닉네임 생성 시 빈 문자열을 사용하는 경우, 정상적으로 생성된다.") {
        shouldNotThrow<IllegalArgumentException> {
            Nickname("")
        }
    }
})