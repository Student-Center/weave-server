package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeamByInvitationCode
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.exception.MeetingTeamException
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetMeetingTeamByInvitationCodeService(
    private val meetingTeamRepository: MeetingTeamRepository,
    private val meetingTeamInvitationService: MeetingTeamInvitationService,
) : GetMeetingTeamByInvitationCode {

    override fun invoke(invitationCode: UUID): MeetingTeam {
        return meetingTeamInvitationService
            .findByInvitationCode(invitationCode = invitationCode)
            ?.let { meetingTeamRepository.getById(it.teamId) }
            ?: throw MeetingTeamException.InvitationCodeNotFound()
    }
}
