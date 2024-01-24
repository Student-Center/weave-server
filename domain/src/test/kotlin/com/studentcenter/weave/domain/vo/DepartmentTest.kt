package com.studentcenter.weave.domain.vo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class DepartmentTest : FunSpec({

    test("학과명은 공백일 수 없습니다.") {
        runCatching { Department(" ") }.onFailure {
            it.message shouldBe "학과명은 공백일 수 없습니다."
        }
    }

    test("학과명은 30자 이하여야 합니다.") {
        runCatching { Department("a".repeat(31)) }.onFailure {
            it.message shouldBe "학과명은 30자 이하여야 합니다."
        }
    }

})
