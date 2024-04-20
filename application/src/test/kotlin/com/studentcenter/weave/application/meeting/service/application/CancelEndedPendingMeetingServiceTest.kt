package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.outbound.MeetingRepositorySpy
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.LocalDateTime

@DisplayName("CancelEndedPendingMeetingServiceTest")
class CancelEndedPendingMeetingServiceTest : DescribeSpec({

    val meetingRepositorySpy = MeetingRepositorySpy()
    val sut = CancelEndedPendingMeetingService(
        meetingRepository = meetingRepositorySpy,
    )

    afterEach {
        meetingRepositorySpy.clear()
    }

    describe("대기 끝난 미팅들 취소 처리 테스트") {
        context("아직 대기 중이 미팅이라면") {
            it("취소 되지 않는다.") {
                // arrange
                val meeting = MeetingFixtureFactory.create().also {
                    meetingRepositorySpy.save(it)
                }

                // act
                sut.invoke()

                // assert
                meetingRepositorySpy.getById(meeting.id).status shouldBe MeetingStatus.PENDING
            }
        }


        context("종료되어야할 펜딩인 미팅이라면") {
            it("취소된다.") {
                // arrange
                val meeting = MeetingFixtureFactory.create(
                    status = MeetingStatus.PENDING,
                    createdAt = LocalDateTime.now().minusDays(Meeting.PENDING_DAYS + 1),
                ).also {
                    meetingRepositorySpy.save(it)
                }

                // act
                sut.invoke()

                // assert
                val updated = meetingRepositorySpy.getById(meeting.id)
                updated.status shouldBe MeetingStatus.CANCELED
                updated.finishedAt shouldNotBe null

            }
        }

        context("종료되어야할 펜딩인 미팅이 여러개라면") {
            it("모두 취소된다.") {
                // arrange
                val pendingMeetingIds = (0..2).map {
                    MeetingFixtureFactory.create().also {
                        meetingRepositorySpy.save(it)
                    }.id
                }.toList()
                val canceledMeetingIds = (0..2).map {
                    MeetingFixtureFactory.create(
                        status = MeetingStatus.PENDING,
                        createdAt = LocalDateTime.now().minusDays(Meeting.PENDING_DAYS + 1),
                    ).also {
                        meetingRepositorySpy.save(it)
                    }.id
                }.toList()

                // act
                sut.invoke()

                // assert
                for (meetingId in pendingMeetingIds) {
                    val updated = meetingRepositorySpy.getById(meetingId)
                    updated.status shouldBe MeetingStatus.PENDING
                    updated.finishedAt shouldBe null
                }
                for (meetingId in canceledMeetingIds) {
                    val updated = meetingRepositorySpy.getById(meetingId)
                    updated.status shouldBe MeetingStatus.CANCELED
                    updated.finishedAt shouldNotBe null
                }

            }
        }
    }

})
