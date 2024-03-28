package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.CreateInvitationLink
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CreateInvitationLinkService(
    private val meetingTeamInvitationService: MeetingTeamInvitationService,
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : CreateInvitationLink {

    @Transactional
    override fun invoke(meetingTeamId: UUID): CreateInvitationLink.Result {
        val currentUserId = getCurrentUserAuthentication().userId

        val meetingTeam = meetingTeamDomainService.getById(meetingTeamId)
            .also {
                validateCurrentUserIsLeader(
                    meetingTeamId = it.id,
                    currentUserId = currentUserId,
                )
                validateTeamStatusIsWaiting(it)
                validateTeamVacancyIsNotFull(it)
            }

        val meetingTeamInvitation = meetingTeamInvitationService.create(meetingTeam.id)

        return CreateInvitationLink.Result(
            meetingTeamInvitationLink = meetingTeamInvitation.invitationLink,
            meetingTeamInvitationCode = meetingTeamInvitation.invitationCode,
        )
    }

    private fun validateCurrentUserIsLeader(
        meetingTeamId: UUID,
        currentUserId: UUID,
    ) {
        val meetingTeamLeader =
            meetingTeamDomainService.getLeaderMemberByMeetingTeamId(meetingTeamId)

        require(meetingTeamLeader.userId == currentUserId) {
            "팀장만 새로운 팀원을 초대할 수 있어요!"
        }
    }

    private fun validateTeamStatusIsWaiting(meetingTeam: MeetingTeam) {
        require(meetingTeam.status == MeetingTeamStatus.WAITING) {
            "팀의 정원이 이미 가득 차 있어서 새로운 팀원을 초대할 수 없어요!"
        }
    }

    private fun validateTeamVacancyIsNotFull(meetingTeam: MeetingTeam) {
        require(meetingTeamDomainService.findAllMeetingMembersByMeetingTeamId(meetingTeam.id).size < meetingTeam.memberCount) {
            "팀의 정원이 이미 가득 차 있어서 새로운 팀원을 초대할 수 없어요!"
        }
    }

}
