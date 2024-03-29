package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingMemberDetail
import com.studentcenter.weave.application.university.port.inbound.GetMajor
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class GetMeetingTeamService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val getUser: GetUser,
    private val getMajor: GetMajor,
    private val getUniversity: GetUniversity,
) : GetMeetingTeam {

    override fun getById(meetingTeamId: UUID): MeetingTeam {
        return meetingTeamDomainService.getById(meetingTeamId)
    }

    override fun getByIdAndStatus(
        meetingTeamId: UUID,
        status: MeetingTeamStatus,
    ): MeetingTeam {
        return meetingTeamDomainService.getByIdAndStatus(meetingTeamId, status)
    }

    override fun getByMemberUserId(memberUserId: UUID): MeetingTeam {
        return meetingTeamDomainService.getByMemberUserId(memberUserId)
    }

    override fun findByMemberUserId(userId: UUID): MeetingTeam? {
        return meetingTeamDomainService.findByMemberUserId(userId)
    }

    override fun findAllMembers(meetingTeamId: UUID): List<MeetingMember> {
        return meetingTeamDomainService.findAllMeetingMembersByMeetingTeamId(meetingTeamId)
    }

    override fun getMeetingTeamMemberSummaryByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary {
        return meetingTeamDomainService.getMeetingTeamMemberSummaryByMeetingTeamId(meetingTeamId)
    }

    override fun getMemberDetail(
        meetingTeamId: UUID,
        memberId: UUID,
    ): MeetingMemberDetail {
        val meetingMember = meetingTeamDomainService.getMember(meetingTeamId, memberId)

        val user = getUser.getById(meetingMember.userId)
        val university = getUniversity.getById(user.universityId)
        val major = getMajor.getById(user.majorId)

        return MeetingMemberDetail.of(user, university, major, meetingMember.role)
    }

}
