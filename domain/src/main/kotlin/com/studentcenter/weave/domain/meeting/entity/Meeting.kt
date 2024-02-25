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
    val pendingEndAt: LocalDateTime = createdAt.plusDays(PENDING_DAYS)

    init {
        require(requestingTeamId != receivingTeamId) {
            "신청하는 팀과 신청 받는 팀은 같을 수 없습니다."
        }
    }

    fun isFinished(): Boolean {
        return status == MeetingStatus.CANCELED || status == MeetingStatus.COMPLETED
    }

    fun cancel(): Meeting {
        verifyNotFinished()

        return copy(
            status = MeetingStatus.CANCELED,
            finishedAt = LocalDateTime.now(),
        )
    }

    fun complete(): Meeting {
        verifyNotFinished()

        return copy(
            status = MeetingStatus.COMPLETED,
            finishedAt = LocalDateTime.now(),
        )
    }

    private fun verifyNotFinished() {
        require(isFinished().not()) { "이미 종료된 미팅은 상태 변경이 불가능합니다." }
    }

    companion object {
        const val PENDING_DAYS = 3L

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
