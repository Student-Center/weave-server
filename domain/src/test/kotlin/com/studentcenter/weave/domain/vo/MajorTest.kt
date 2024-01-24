package com.studentcenter.weave.domain.vo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MajorTest : FunSpec({

    test("전공명은 공백일 수 없습니다.") {
        runCatching { Major(" ") }.onFailure {
            it.message shouldBe "전공명은 공백일 수 없습니다."
        }
    }

    test("전공명은 30자 이하여야 합니다.") {
        runCatching { Major("a".repeat(31)) }.onFailure {
            it.message shouldBe "전공명은 30자 이하여야 합니다."
        }
    }

})
