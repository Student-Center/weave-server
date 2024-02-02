package com.studentcenter.weave.domain.user.vo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.types.shouldBeInstanceOf

class MbtiTest : FunSpec({

    test("MBTI 형식 테스트 1") {
        Mbti("EnTJ")
        Mbti("ISTP")
        Mbti("ESFJ")
        Mbti("INFP")
        Mbti("enFp")
    }

    test("MBTI 형식 테스트 2") {
        runCatching { Mbti("EnT") }
            .exceptionOrNull().shouldBeInstanceOf<IllegalArgumentException>()

        runCatching { Mbti("EnTJJ") }
            .exceptionOrNull().shouldBeInstanceOf<IllegalArgumentException>()

        runCatching { Mbti("EnT1") }
            .exceptionOrNull().shouldBeInstanceOf<IllegalArgumentException>()

        runCatching { Mbti("EnT!") }
            .exceptionOrNull().shouldBeInstanceOf<IllegalArgumentException>()
    }

})
