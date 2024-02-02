package com.studentcenter.weave.domain.user.vo

import com.studentcenter.weave.domain.user.vo.BirthYear
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Year

class BirthYearTest : FunSpec({

    test("생년월일은 1900년 이후, 현재 년도 이전이어야 합니다") {
        BirthYear(1900)
        BirthYear(2021)
    }

    test("생년월일이 1900년 이후, 현재 년도 이전이 아니면 예외가 발생합니다") {
        runCatching { BirthYear(1899) }.isFailure shouldBe true
        runCatching { BirthYear(Year.now().value + 1) }.isFailure shouldBe true
    }

})
