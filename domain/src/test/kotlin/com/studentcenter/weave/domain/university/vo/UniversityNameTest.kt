package com.studentcenter.weave.domain.university.vo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class UniversityNameTest : FunSpec({

    test("대학교 이름은 공백일 수 없습니다.") {
        runCatching { UniversityName(" ") }.onFailure {
            it.message shouldBe "대학교 이름은 공백일 수 없습니다."
        }
    }

    test("대학교 이름은 30자 이하여야 합니다.") {
        runCatching { UniversityName("a".repeat(31)) }.onFailure {
            it.message shouldBe "대학교 이름은 30자 이하여야 합니다."
        }
    }

})
