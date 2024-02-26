package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import java.time.LocalDateTime
import java.util.*

data class MeetingResponse(val meeting : MeetingDTO? = null) {

    data class MeetingDTO(
        val id: UUID,
        val requestingTeamId: UUID,
        val receivingTeamId: UUID,
        val status: MeetingStatus = MeetingStatus.PENDING,
        val createdAt: LocalDateTime,
        val pendingEndAt: LocalDateTime,
    )

    companion object {

        fun from(meeting: Meeting?): MeetingResponse {
            if (meeting == null) return MeetingResponse()

            return MeetingResponse(
                meeting = MeetingDTO(
                    id = meeting.id,
                    requestingTeamId = meeting.requestingTeamId,
                    receivingTeamId = meeting.receivingTeamId,
                    status = meeting.status,
                    createdAt = meeting.createdAt,
                    pendingEndAt = meeting.pendingEndAt,
                )
            )
        }

    }
}
