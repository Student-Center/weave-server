package com.studentcenter.weave.support.common.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class EmailTest: FunSpec({

    test("이메일 주소가 올바른 형식이 아닌 경우 예외가 발생한다.") {
        val invalidEmail = "invalid_email"
        shouldThrow<IllegalArgumentException> {
            Email(invalidEmail)
        }
    }

    test("이메일 주소가 올바른 형식인 경우 객체가 생성된다.") {
        val validEmail = "test1234@test.com"
        Email(validEmail)
    }
})
