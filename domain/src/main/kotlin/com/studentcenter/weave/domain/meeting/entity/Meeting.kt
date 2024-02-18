package com.studentcenter.weave.domain.meeting.entity

import com.studentcenter.weave.domain.meeting.enums.MeetingStatus
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class Meeting(
    val id: UUID = UuidCreator.create(),
    val requestingTeamId: UUID,
    val receivingTeamId: UUID,
    val status: MeetingStatus = MeetingStatus.PENDING,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val finishedAt: LocalDateTime? = null,
) {

    init {
        require(requestingTeamId != receivingTeamId) {
            "신청하는 팀과 신청 받는 팀은 같을 수 없습니다."
        }
    }

    fun isFinished(): Boolean {
        return status == MeetingStatus.CANCELED || status == MeetingStatus.COMPLETED
    }

    fun cancel(): Meeting {
        return copy(
            status = MeetingStatus.CANCELED,
            finishedAt = LocalDateTime.now(),
        )
    }

    fun complete(): Meeting {
        return copy(
            status = MeetingStatus.COMPLETED,
            finishedAt = LocalDateTime.now(),
        )
    }

    companion object {

        fun create(
            requestingTeamId: UUID,
            receivingTeamId: UUID,
        ): Meeting {
            return Meeting(
                requestingTeamId = requestingTeamId,
                receivingTeamId = receivingTeamId,
            )
        }
    }

}
