package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.exception.MeetingTeamExceptionType
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeamByInvitationCode
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetMeetingTeamByInvitationCodeService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val meetingTeamInvitationService: MeetingTeamInvitationService,
) : GetMeetingTeamByInvitationCode {

    override fun invoke(invitationCode: UUID): MeetingTeam {

        return meetingTeamInvitationService.findByInvitationCode(invitationCode = invitationCode)
            ?.let {
                meetingTeamDomainService.getById(it.teamId)
            } ?: throw CustomException(
            type = MeetingTeamExceptionType.INVITATION_CODE_NOT_FOUND,
            message = "초대 링크를 찾을 수 없어요! 새로운 초대 링크를 통해 입장해 주세요!"
        )
    }
}
