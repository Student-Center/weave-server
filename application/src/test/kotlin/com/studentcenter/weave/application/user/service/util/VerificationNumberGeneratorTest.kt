package com.studentcenter.weave.application.user.service.util

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class VerificationNumberGeneratorTest : DescribeSpec({

    describe("VerificationNumberGenerator 생성 테스트") {
        context("원하는 크기를 넣으면") {
            it("해당 크기의 검증 번호를 생성한다.") {
                // arrange
                val targetSize = 6

                // act
                val generatedNumbers = List(10) {
                    VerificationNumberGenerator.generate(targetSize)
                }

                // assert
                generatedNumbers.forEach {
                    it.length shouldBeEqual targetSize
                    it.forEach { c -> c.isDigit() shouldBeEqual true }
                }
            }
        }
    }

})
