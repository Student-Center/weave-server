package com.studentcenter.weave.domain.user.vo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class MbtiTest : FunSpec({

    context("MBTI 유효성 검사") {
        test("유효한 MBTI 형식 검사") {
            Mbti("EnTJ")
            Mbti("ISTP")
            Mbti("ESFJ")
            Mbti("INFP")
            Mbti("enFp")
        }

        test("유효하지 않은 MBTI 형식 검사") {
            runCatching { Mbti("EnT") }.exceptionOrNull()
                ?.shouldBeInstanceOf<IllegalArgumentException>()
            runCatching { Mbti("EnTJJ") }.exceptionOrNull()
                ?.shouldBeInstanceOf<IllegalArgumentException>()
            runCatching { Mbti("EnT1") }.exceptionOrNull()
                ?.shouldBeInstanceOf<IllegalArgumentException>()
            runCatching { Mbti("EnT!") }.exceptionOrNull()
                ?.shouldBeInstanceOf<IllegalArgumentException>()
        }
    }

    context("대표 MBTI 계산") {
        test("대표 MBTI 계산 - 다수의 MBTI") {
            val mbtis = listOf(
                Mbti("EnTJ"),
                Mbti("ISTP"),
                Mbti("ESFJ"),
                Mbti("INFP"),
                Mbti("enFp")
            )
            Mbti.getDominantMbti(mbtis).value shouldBe "ENFP"
        }

        test("대표 MBTI 계산 - 3개의 MBTI") {
            val mbtis = listOf(
                Mbti("EnTJ"),
                Mbti("ISTP"),
                Mbti("ESFJ"),
            )
            Mbti.getDominantMbti(mbtis).value shouldBe "ESTJ"
        }

        test("대표 MBTI 계산 - 2개의 MBTI") {
            val mbtis = listOf(
                Mbti("EnTJ"),
                Mbti("ISTP"),
            )
            Mbti.getDominantMbti(mbtis).value shouldBe "ESTP"
        }
    }
})
