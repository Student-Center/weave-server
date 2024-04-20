package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.outbound.MeetingAttendanceRepositorySpy
import com.studentcenter.weave.application.meeting.service.domain.impl.MeetingAttendanceDomainServiceImpl
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendanceFixtureFactory
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("GetMeetingAttendancesServiceTest")
class GetMeetingAttendancesServiceTest : DescribeSpec({

    val meetingAttendanceRepositorySpy = MeetingAttendanceRepositorySpy()
    val meetingAttendanceDomainService = MeetingAttendanceDomainServiceImpl(
        meetingAttendanceRepository = meetingAttendanceRepositorySpy,
    )
    val sut = GetMeetingAttendancesService(
        meetingAttendanceDomainService = meetingAttendanceDomainService,
    )

    afterEach {
        meetingAttendanceRepositorySpy.clear()
        SecurityContextHolder.clearContext()
    }

    describe("미팅 참여 정보 조회 유스케이스") {
        context("참여자가 있는 경우") {
            it("참여 정보가 조회된다") {
                // arrange
                val expectedAttendanceSize = 2
                val meeting = MeetingFixtureFactory.create()
                repeat(expectedAttendanceSize) {
                    meetingAttendanceRepositorySpy.save(
                        MeetingAttendanceFixtureFactory.create(meetingId = meeting.id)
                    )
                }

                // act
                val meetingAttendances = sut.invoke(meeting.id)

                // assert
                meetingAttendances.size shouldBe expectedAttendanceSize
            }
        }
    }

})
