package com.studentcenter.weave.application.meetingTeam.service.domain

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import com.studentcenter.weave.domain.user.vo.MbtiAffinityScore
import java.util.*

interface MeetingTeamDomainService {

    /**
     * 팀 간 MBTI 궁합 점수를 계산해요
     * @return null - 팀간 MBTI 궁합 점수를 계산할 수 없는 경우
     */
    fun calculateTeamMbtiAffinityScore(
        meetingTeam: MeetingTeam,
        targetMeetingTeam: MeetingTeam,
    ): MbtiAffinityScore?


    fun getMeetingTeamMemberSummaryByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary

}
