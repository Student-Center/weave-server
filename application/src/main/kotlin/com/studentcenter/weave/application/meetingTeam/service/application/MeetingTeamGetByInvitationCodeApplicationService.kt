package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.exception.MeetingTeamExceptionType
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetByInvitationCodeUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamInvitation
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeetingTeamGetByInvitationCodeApplicationService(
    val meetingTeamDomainService: MeetingTeamDomainService,
    val meetingTeamInvitationService: MeetingTeamInvitationService,
) : MeetingTeamGetByInvitationCodeUseCase {

    override fun invoke(invitationCode: UUID): MeetingTeam {
        val meetingTeamInvitation =
            meetingTeamInvitationService.findByInvitationCode(invitationCode = invitationCode)

        validateInvitationCodeExists(meetingTeamInvitation)
            .let { return meetingTeamDomainService.getById(it.teamId) }
    }

    private fun validateInvitationCodeExists(meetingTeamInvitation: MeetingTeamInvitation?): MeetingTeamInvitation {
        if (meetingTeamInvitation == null) {
            throw CustomException(
                type = MeetingTeamExceptionType.INVITATION_CODE_NOT_FOUND,
                message = "초대 코드가 존재하지 않습니다."
            )
        }

        return meetingTeamInvitation
    }

}
