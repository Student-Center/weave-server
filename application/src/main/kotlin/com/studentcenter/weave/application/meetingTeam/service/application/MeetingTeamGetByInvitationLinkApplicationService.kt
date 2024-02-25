package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.exception.MeetingTeamExceptionType
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetByInvitationLinkUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Service

@Service
class MeetingTeamGetByInvitationLinkApplicationService(
    val meetingTeamDomainService: MeetingTeamDomainService,
    val meetingTeamInvitationService: MeetingTeamInvitationService,
) : MeetingTeamGetByInvitationLinkUseCase {

    override fun invoke(invitationLink: Url): MeetingTeam {
        val meetingTeamInvitation =
            meetingTeamInvitationService.getByInvitationLink(invitationLink = invitationLink)

        validateInvitationLinkExists(meetingTeamInvitation)
            .let { return meetingTeamDomainService.getById(it.teamId) }
    }

    private fun validateInvitationLinkExists(meetingTeamInvitation: MeetingTeamInvitation?): MeetingTeamInvitation {
        if (meetingTeamInvitation == null) {
            throw CustomException(
                type = MeetingTeamExceptionType.INVITATION_LINK_NOT_FOUND,
                message = "초대 링크가 존재하지 않습니다."
            )
        }

        return meetingTeamInvitation
    }

}
