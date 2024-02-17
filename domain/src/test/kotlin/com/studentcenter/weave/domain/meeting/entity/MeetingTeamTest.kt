package com.studentcenter.weave.domain.meeting.entity

import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class MeetingTest : FunSpec({

    test("미팅 생성시 Pending 상태로 생성된다.") {
        // arrange, act
        val domain = Meeting.create(
            requestingTeamId = UuidCreator.create(),
            receivingTeamId = UuidCreator.create(),
        )

        // assert
        domain.status shouldBe MeetingStatus.PENDING
    }

    test("미팅 생성시 요청 팀과 수신 팀은 같을 수 없다.") {
        // arrange
        val teamId = UuidCreator.create()

        // act, assert
        shouldThrow<IllegalArgumentException> {
            Meeting.create(
                requestingTeamId = teamId,
                receivingTeamId = teamId,
            )
        }
    }

})
