package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.*

object MeetingTeamMemberSummaryFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        meetingTeamId: UUID = UuidCreator.create(),
        teamMbti: Mbti = Mbti("INTJ"),
        youngestMemberBirthYear: BirthYear = BirthYear(2005),
        oldestMemberBirthYear: BirthYear = BirthYear(2000),
    ): MeetingTeamMemberSummary {
        return MeetingTeamMemberSummary(
            id = id,
            meetingTeamId = meetingTeamId,
            teamMbti = teamMbti,
            youngestMemberBirthYear = youngestMemberBirthYear,
            oldestMemberBirthYear = oldestMemberBirthYear,
        )
    }

}
