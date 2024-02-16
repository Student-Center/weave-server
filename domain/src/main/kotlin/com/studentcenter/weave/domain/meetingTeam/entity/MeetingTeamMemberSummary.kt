package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Mbti
import java.time.LocalDateTime
import java.util.*

data class MeetingTeamMemberSummary(
    val id: UUID,
    val meetingTeamId: UUID,
    val teamMbti: Mbti,
    val minBirthYear: BirthYear,
    val maxBirthYear: BirthYear,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    init {
        require(minBirthYear.value >= maxBirthYear.value) {
            "최소 년생은 최대 년생보다 작을 수 없어요!"
        }
    }

}
