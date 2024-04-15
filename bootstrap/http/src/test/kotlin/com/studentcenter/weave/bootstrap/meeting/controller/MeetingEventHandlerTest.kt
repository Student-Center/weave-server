package com.studentcenter.weave.bootstrap.meeting.controller

import com.ninjasquad.springmockk.MockkBean
import com.studentcenter.weave.application.chat.port.inbound.CreateChatRoom
import com.studentcenter.weave.bootstrap.integration.IntegrationTestDescribeSpec
import com.studentcenter.weave.domain.meeting.entity.MeetingFixtureFactory
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.domain.meeting.event.MeetingCompletedEvent.Companion.createCompletedEvent
import io.kotest.core.annotation.DisplayName
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher

@DisplayName("MeetingEventHandler 테스트")
class MeetingEventHandlerTest(
    private val applicationEventPublisher: ApplicationEventPublisher,
    @MockkBean
    private val createChatRoom: CreateChatRoom,
) : IntegrationTestDescribeSpec({

    describe("미팅 완료 이벤트를 전달 받으면") {
        it("새로운 채팅방을 생성한다") {
            // arrange
            val meetingFixture = MeetingFixtureFactory.create(status = MeetingStatus.COMPLETED)
            val meetingCompletedEvent = meetingFixture.createCompletedEvent()

            every { createChatRoom.invoke(meetingCompletedEvent.entity) } just runs

            // act
            applicationEventPublisher.publishEvent(meetingCompletedEvent)

            // assert
            verify { createChatRoom.invoke(meetingCompletedEvent.entity) }
        }
    }

})
