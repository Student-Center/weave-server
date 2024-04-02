package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamMemberSummaryRepository
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meetingTeam.vo.MeetingMemberDetail
import com.studentcenter.weave.application.university.port.inbound.GetMajor
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class GetMeetingTeamService(
    private val meetingTeamRepository: MeetingTeamRepository,
    private val meetingTeamMemberSummaryRepository: MeetingTeamMemberSummaryRepository,
    private val getUser: GetUser,
    private val getMajor: GetMajor,
    private val getUniversity: GetUniversity,
) : GetMeetingTeam {

    override fun getById(meetingTeamId: UUID): MeetingTeam {
        return meetingTeamRepository.getById(meetingTeamId)
    }

    override fun getByIdAndStatus(
        meetingTeamId: UUID,
        status: MeetingTeamStatus,
    ): MeetingTeam {
        return meetingTeamRepository.getByIdAndStatus(meetingTeamId, status)
    }

    override fun getByMemberUserId(memberUserId: UUID): MeetingTeam {
        return meetingTeamRepository.getByMemberUserId(memberUserId)
    }

    override fun findByMemberUserId(userId: UUID): MeetingTeam? {
        return meetingTeamRepository.findByMemberUserId(userId)
    }

    override fun getMeetingTeamMemberSummaryByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary {
        return meetingTeamMemberSummaryRepository.getByMeetingTeamId(meetingTeamId)
    }

    override fun getMemberDetail(
        meetingTeamId: UUID,
        memberId: UUID,
    ): MeetingMemberDetail {
        val meetingMember = meetingTeamRepository
            .getById(meetingTeamId)
            .members
            .find { it.userId == memberId }
            ?: throw IllegalArgumentException("해당 멤버가 팀에 속해있지 않아요")

        val user = getUser.getById(meetingMember.userId)
        val university = getUniversity.getById(user.universityId)
        val major = getMajor.getById(user.majorId)

        return MeetingMemberDetail.of(user, university, major, meetingMember.role)
    }

}
