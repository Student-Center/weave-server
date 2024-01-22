package com.studentcenter.weave.support.common.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec

class UrlTest : FunSpec({

    test("URL 형식이 아닌 경우 예외가 발생한다.") {
        val invalidUrl = "invalid_url"
        shouldThrow<IllegalArgumentException> {
            Url(invalidUrl)
        }
    }

    test("URL 형식인 경우 객체가 생성된다.") {
        val validUrl = "https://test.com"
        Url(validUrl)
    }

})
