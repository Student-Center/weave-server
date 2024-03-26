package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamQueryUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeetingTeamQueryApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : MeetingTeamQueryUseCase {

    override fun getById(meetingTeamId: UUID): MeetingTeam {
        return meetingTeamDomainService.getById(meetingTeamId)
    }

    override fun getByIdAndStatus(
        meetingTeamId: UUID,
        status: MeetingTeamStatus
    ): MeetingTeam {
        return meetingTeamDomainService.getByIdAndStatus(meetingTeamId, status)
    }

    override fun getByMemberUserId(memberUserId: UUID): MeetingTeam {
        return meetingTeamDomainService.getByMemberUserId(memberUserId)
    }

    override fun findByMemberUserId(userId: UUID): MeetingTeam? {
        return meetingTeamDomainService.findByMemberUserId(userId)
    }

    override fun findAllMeetingMembersByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember> {
        return meetingTeamDomainService.findAllMeetingMembersByMeetingTeamId(meetingTeamId)
    }

    override fun getMeetingTeamMemberSummaryByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary {
        return meetingTeamDomainService.getMeetingTeamMemberSummaryByMeetingTeamId(meetingTeamId)
    }

}
