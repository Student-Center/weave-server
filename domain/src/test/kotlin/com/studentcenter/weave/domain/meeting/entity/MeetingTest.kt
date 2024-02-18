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

    test("취소 된 미팅의 상태는 취소 상태이고 종료되어야 한다.") {
        // arrange
        val meeting = MeetingFixtureFactory.create()

        // act
        val canceledMeeting = meeting.cancel()

        // assert
        canceledMeeting.status shouldBe MeetingStatus.CANCELED
        canceledMeeting.isFinished() shouldBe true
    }

    test("이미 취소된 미팅을 취소할 경우 예외를 던진다.") {
        // arrange
        val meeting = MeetingFixtureFactory
            .create()
            .cancel()

        // act, assert
        shouldThrow<IllegalArgumentException> {
            meeting.cancel()
        }
    }

    test("이미 완료된 미팅을 취소할 경우 예외를 던진다.") {
        // arrange
        val meeting = MeetingFixtureFactory
            .create()
            .complete()

        // act, assert
        shouldThrow<IllegalArgumentException> {
            meeting.cancel()
        }
    }

    test("완료 된 미팅의 상태는 완료 상태이고 종료되어야 한다.") {
        // arrange
        val meeting = MeetingFixtureFactory.create()

        // act
        val canceledMeeting = meeting.complete()

        // assert
        canceledMeeting.status shouldBe MeetingStatus.COMPLETED
        canceledMeeting.isFinished() shouldBe true
    }

    test("이미 취소된 미팅을 완료할 경우 예외를 던진다.") {
        // arrange
        val meeting = MeetingFixtureFactory
            .create()
            .cancel()

        // act, assert
        shouldThrow<IllegalArgumentException> {
            meeting.complete()
        }
    }

    test("이미 완료된 미팅을 완료할 경우 예외를 던진다.") {
        // arrange
        val meeting = MeetingFixtureFactory
            .create()
            .complete()

        // act, assert
        shouldThrow<IllegalArgumentException> {
            meeting.complete()
        }
    }

})
