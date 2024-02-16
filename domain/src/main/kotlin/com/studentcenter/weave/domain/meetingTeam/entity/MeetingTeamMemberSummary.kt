package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class MeetingTeamMemberSummary(
    val id: UUID = UuidCreator.create(),
    val meetingTeamId: UUID,
    val teamMbti: Mbti,
    val youngestMemberBirthYear: BirthYear,
    val oldestMemberBirthYear: BirthYear,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    init {
        require(youngestMemberBirthYear.value >= oldestMemberBirthYear.value) {
            "가장 나이가 어린 멤버의 년생은 가장 나이가 많은 멤버의 년생보다 작아야 합니다."
        }
    }

}
