package com.studentcenter.weave.domain.university.entity

import com.studentcenter.weave.domain.university.vo.MajorName
import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeLessThanOrEqualTo
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class MajorTest : FunSpec({

    test("대학교 생성") {
        // arrange
        val name = MajorName(value = "컴퓨터공학과")

        // act
        val major = Major.create(
            univId = UuidCreator.create(),
            name = name
        )

        // assert
        major.name shouldBe name
        major.createdAt shouldBeLessThanOrEqualTo LocalDateTime.now()
    }

})
