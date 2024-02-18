package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamCreateInvitationUseCase
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.application.user.port.inbound.UserQueryUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class MeetingTeamCreateInvitationApplicationService(
    private val meetingTeamInvitationService: MeetingTeamInvitationService,
    private val userQueryUseCase: UserQueryUseCase,
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val meetingMemberRepository: MeetingMemberRepository,
) : MeetingTeamCreateInvitationUseCase {

    @Transactional
    override fun invoke(command: MeetingTeamCreateInvitationUseCase.Command): MeetingTeamCreateInvitationUseCase.Result {
        val meetingTeam = meetingTeamDomainService.getById(command.meetingTeamId)
            .also {
                validate(it.id)
            }

        val invitationCode = meetingTeamInvitationService.create(meetingTeam.id)

        return MeetingTeamCreateInvitationUseCase.Result(
            teamId = meetingTeam.id,
            invitationCode = invitationCode,
        )

    }

    private fun validate(meetingTeamId: UUID) {
        validateCurrentUserIsLeader(meetingTeamId)
        validateTeamVacancy(meetingTeamId)
    }

    private fun validateCurrentUserIsLeader(meetingTeamId: UUID) {
        val currentUser = userQueryUseCase.getById(
            getCurrentUserAuthentication().userId
        )
        val meetingTeamLeader = meetingTeamDomainService.getLeaderMemberByMeetingTeamId(meetingTeamId)

        require(meetingTeamLeader.userId == currentUser.id) {
            "팀장만 새로운 팀원을 초대할 수 있어요!"
        }

    }

    private fun validateTeamVacancy(meetingTeamId: UUID) {
        val memberCount = meetingTeamDomainService.getById(meetingTeamId).memberCount

        require(meetingMemberRepository.findAllByMeetingTeamId(meetingTeamId).size < memberCount) {
            "팀의 정원이 이미 가득 차 있어서 새로운 팀원을 초대할 수 없어요!"
        }

    }
    
}
