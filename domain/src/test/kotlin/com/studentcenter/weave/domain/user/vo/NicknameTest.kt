package com.studentcenter.weave.domain.user.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@DisplayName("NicknameTest")
class NicknameTest : FunSpec ({

    test("최대 글자수 기준(10) 보다 긴 닉네임을 생성하는 경우, 에러가 발생한다.") {

        val exception = shouldThrow<IllegalArgumentException> {
            Nickname("닉".repeat(11))
        }

        exception.message shouldBe "닉네임은 ${Nickname.MIN_LENGTH}글자 이상 ${Nickname.MAX_LENGTH}자 이하여야 합니다."
    }

    test("최소 글자수 기준(1) 보다 짧은 길이의 닉네임을 생성하는 경우, 에러가 발생한다.") {

        val exception = shouldThrow<IllegalArgumentException> {
            Nickname("")
        }

        exception.message shouldBe "닉네임은 ${Nickname.MIN_LENGTH}글자 이상 ${Nickname.MAX_LENGTH}자 이하여야 합니다."
    }
})
