package com.studentcenter.weave.application.meetingTeam.service.domain.impl

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamMemberSummaryRepository
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.user.vo.MbtiAffinityScore
import com.studentcenter.weave.domain.user.vo.MbtiAffinityScore.Companion.getAffinityScore
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeetingTeamDomainServiceImpl(
    private val meetingTeamMemberSummaryRepository: MeetingTeamMemberSummaryRepository,
) : MeetingTeamDomainService {

    override fun calculateTeamMbtiAffinityScore(
        meetingTeam: MeetingTeam,
        targetMeetingTeam: MeetingTeam,
    ): MbtiAffinityScore? {
        if (meetingTeam.status != MeetingTeamStatus.PUBLISHED || targetMeetingTeam.status != MeetingTeamStatus.PUBLISHED) {
            return null
        }
        if (meetingTeam.id == targetMeetingTeam.id) {
            return null
        }
        val mbti = meetingTeamMemberSummaryRepository
            .getByMeetingTeamId(meetingTeam.id)
            .teamMbti
        val targetMbti = meetingTeamMemberSummaryRepository
            .getByMeetingTeamId(targetMeetingTeam.id)
            .teamMbti
        return mbti.getAffinityScore(targetMbti)
    }

    override fun getMeetingTeamMemberSummaryByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary {
        return meetingTeamMemberSummaryRepository.getByMeetingTeamId(meetingTeamId)
    }

}
