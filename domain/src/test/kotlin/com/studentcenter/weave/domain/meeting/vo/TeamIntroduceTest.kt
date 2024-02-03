package com.studentcenter.weave.domain.meeting.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class TeamIntroduceTest : FunSpec({

    test("팀 한줄 소개는 1자 이상 10자 이하여야 합니다.") {
        shouldThrow<IllegalArgumentException> {
            TeamIntroduce("")
        }

        shouldThrow<IllegalArgumentException> {
            TeamIntroduce("팀".repeat(11))
        }
    }

})
