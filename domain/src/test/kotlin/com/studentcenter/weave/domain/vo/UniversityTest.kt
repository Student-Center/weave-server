package com.studentcenter.weave.domain.vo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class UniversityTest : FunSpec({

    test("대학교 이름은 공백일 수 없습니다.") {
        runCatching { University(" ") }.onFailure {
            it.message shouldBe "대학교 이름은 공백일 수 없습니다."
        }
    }

    test("대학교 이름은 30자 이하여야 합니다.") {
        runCatching { University("a".repeat(31)) }.onFailure {
            it.message shouldBe "대학교 이름은 30자 이하여야 합니다."
        }
    }

})
