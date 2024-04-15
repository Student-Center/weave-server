package com.studentcenter.weave.domain.meeting.event

import com.studentcenter.weave.domain.common.DomainEvent
import com.studentcenter.weave.domain.meeting.entity.Meeting

data class MeetingCompletedEvent(
    override val entity: Meeting,
) : DomainEvent<Meeting> {

    companion object {

        fun Meeting.createCompletedEvent(): MeetingCompletedEvent {
            require(isCompleted()) { "미팅이 완료되지 않았습니다." }

            return MeetingCompletedEvent(this)
        }

    }

}
