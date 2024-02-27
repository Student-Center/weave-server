package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.exception.MeetingTeamExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamEnterUseCase
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeetingTeamEnterApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val meetingTeamInvitationService: MeetingTeamInvitationService,
    private val userQueryUseCase: UserQueryUseCase,
) : MeetingTeamEnterUseCase {

    override fun invoke(invitationCode: UUID) {
        val currentUser = getCurrentUserAuthentication().userId
            .let { userQueryUseCase.getById(it) }

        val meetingTeam =
            meetingTeamInvitationService.findByInvitationCode(invitationCode = invitationCode)
                ?.let {
                    meetingTeamDomainService.getById(it.teamId)
                } ?: throw CustomException(
                type = MeetingTeamExceptionType.INVITATION_CODE_NOT_FOUND,
                message = "초대 링크를 찾을 수 없어요! 새로운 초대 링크를 통해 입장해 주세요!"
            )

        meetingTeamDomainService.addMember(
            meetingTeam = meetingTeam,
            user = currentUser,
            role = MeetingMemberRole.MEMBER,
        ).takeIf { isMeetingTeamFull(meetingTeam) }
            ?.let { meetingTeamDomainService.publishById(meetingTeam.id) }

    }

    private fun isMeetingTeamFull(meetingTeam: MeetingTeam): Boolean {
        return meetingTeamDomainService.countByMeetingTeamId(meetingTeamId = meetingTeam.id) == meetingTeam.memberCount
    }

}
