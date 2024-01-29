package com.studentcenter.weave.domain.entity

import com.studentcenter.weave.domain.vo.UniversityName
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeLessThanOrEqualTo
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class UniversityTest : FunSpec({

    test("대학교 생성") {
        // arrange
        val name = UniversityName("명지대학교")
        val domainAddress = "mju.ac.kr"
        val logoAddress = "/public/university/1/logo"

        // act
        val univ = University.create(
            name = name,
            domainAddress = domainAddress,
            logoAddress = logoAddress,
        )

        // assert
        univ.name shouldBe name
        univ.domainAddress shouldBe domainAddress
        univ.logoAddress shouldBe logoAddress
        univ.createdAt shouldBeLessThanOrEqualTo LocalDateTime.now()
    }

})
