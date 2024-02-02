package com.studentcenter.weave.domain.user.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class HeightTest : FunSpec({

    context("생성") {

        test("키는 1cm 이상 300cm 미만이어야 한다.") {
            Height(1)
            Height(299)
        }

        test("키는 1cm 미만이면 예외가 발생한다.") {
            shouldThrow<IllegalArgumentException> {
                Height(0)
            }
        }

        test("키는 300cm 초과면 예외가 발생한다.") {
            shouldThrow<IllegalArgumentException> {
                Height(301)
            }
        }

    }

})
