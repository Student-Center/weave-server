package com.studentcenter.weave.domain.meeting.entity

import com.studentcenter.weave.domain.meeting.enums.MeetingRequestStatus
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class MeetingRequest(
    val id: UUID = UuidCreator.create(),
    val requestingMeetingTeamId: UUID,
    val receivingMeetingTeamId: UUID,
    val status: MeetingRequestStatus = MeetingRequestStatus.REQUESTED,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val respondAt: LocalDateTime? = null,
) {

    init {
        require(requestingMeetingTeamId != receivingMeetingTeamId) {
            "미팅을 신청하는 팀과 받는 팀은 같을 수 없어요"
        }
    }

    fun response(responseStatus: MeetingRequestStatus): MeetingRequest {
        require(this.status == MeetingRequestStatus.REQUESTED && respondAt == null) {
            "이미 처리된 신청이에요"
        }
        return copy(
            status = responseStatus,
            respondAt = LocalDateTime.now()
        )
    }

    companion object {

        fun create(
            requestingMeetingTeamId: UUID,
            receivingMeetingTeamId: UUID,
        ): MeetingRequest {
            return MeetingRequest(
                requestingMeetingTeamId = requestingMeetingTeamId,
                receivingMeetingTeamId = receivingMeetingTeamId,
            )
        }
    }

}

