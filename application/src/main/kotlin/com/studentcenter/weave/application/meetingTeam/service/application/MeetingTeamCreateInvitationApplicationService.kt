package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamCreateInvitationUseCase
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.util.MeetingTeamInvitationService
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class MeetingTeamCreateInvitationApplicationService(
    private val meetingTeamInvitationService: MeetingTeamInvitationService,
    private val meetingTeamDomainService: MeetingTeamDomainService,
    private val meetingMemberRepository: MeetingMemberRepository,
) : MeetingTeamCreateInvitationUseCase {

    @Transactional
    override fun invoke(meetingTeamId: UUID): MeetingTeamCreateInvitationUseCase.Result {
        val currentUserId = getCurrentUserAuthentication().userId

        val meetingTeam = meetingTeamDomainService.getById(meetingTeamId)
            .also {
                validate(
                    meetingTeamId = it.id,
                    currentUserId = currentUserId,
                )
            }

        val invitationCode = meetingTeamInvitationService.create(meetingTeam.id)

        return MeetingTeamCreateInvitationUseCase.Result(
            teamId = meetingTeam.id,
            invitationCode = invitationCode,
        )
    }

    private fun validate(
        meetingTeamId: UUID,
        currentUserId: UUID,
    ) {
        validateCurrentUserIsLeader(
            meetingTeamId = meetingTeamId,
            currentUserId = currentUserId,
        )
        validateTeamVacancy(meetingTeamId)
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

    private fun validateTeamVacancy(meetingTeamId: UUID) {
        val meetingTeam = meetingTeamDomainService.getById(meetingTeamId)

        require(
            validateTeamVacancyIsNotFull(meetingTeam) && validateMeetingTeamStateIsWaiting(
                meetingTeam
            )
        ) {
            "팀의 정원이 이미 가득 차 있어서 새로운 팀원을 초대할 수 없어요!"
        }
    }

    private fun validateTeamVacancyIsNotFull(meetingTeam: MeetingTeam): Boolean {
        return meetingMemberRepository.findAllByMeetingTeamId(meetingTeam.id).size < meetingTeam.memberCount
    }

    private fun validateMeetingTeamStateIsWaiting(meetingTeam: MeetingTeam): Boolean {
        return meetingTeam.status == MeetingTeamStatus.WAITING
    }
}
