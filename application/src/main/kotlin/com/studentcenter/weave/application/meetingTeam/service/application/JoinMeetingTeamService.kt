package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.JoinMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamMemberSummaryRepository
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary.Companion.createSummary
import com.studentcenter.weave.domain.meetingTeam.exception.MeetingTeamException
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class JoinMeetingTeamService(
    private val meetingTeamRepository: MeetingTeamRepository,
    private val meetingTeamInvitationService: MeetingTeamInvitationService,
    private val meetingTeamMemberSummaryRepository: MeetingTeamMemberSummaryRepository,
    private val getUser: GetUser,
) : JoinMeetingTeam {

    override fun invoke(invitationCode: UUID) {
        val currentUser: User = getCurrentUserAuthentication().userId
            .let { getUser.getById(it) }

        val meetingTeam = meetingTeamInvitationService
            .findByInvitationCode(invitationCode)
            ?.let { meetingTeamRepository.getById(it.teamId) }
            ?: throw MeetingTeamException.InvitationCodeNotFound()

        val memberJoinedMeetingTeam: MeetingTeam = meetingTeam
            .joinMember(currentUser)
            .also { meetingTeamRepository.save(it) }

        if (memberJoinedMeetingTeam.isPublished()) {
            val summary: MeetingTeamMemberSummary =
                memberJoinedMeetingTeam.createSummary { getUsersByMeetingMembers(it) }
            meetingTeamMemberSummaryRepository.save(summary)
        }
    }

    private fun getUsersByMeetingMembers(members: List<MeetingMember>): List<User> {
        return members
            .map { it.userId }
            .let { getUser.getAllByIds(it) }
    }

}
