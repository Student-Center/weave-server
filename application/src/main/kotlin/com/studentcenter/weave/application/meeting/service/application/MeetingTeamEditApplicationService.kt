package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamEditUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingTeamDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MeetingTeamEditApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : MeetingTeamEditUseCase {

    @Transactional
    override fun invoke(command: MeetingTeamEditUseCase.Command) {
        meetingTeamDomainService
            .getById(command.id)
            .also {
                validateCurrentUserIsLeader(it.id)
                updateMeetingTeam(it.id, command)
            }
    }

    private fun updateMeetingTeam(
        meetingTeamId: UUID,
        command: MeetingTeamEditUseCase.Command
    ) {
        meetingTeamDomainService.updateById(
            id = meetingTeamId,
            location = command.location,
            memberCount = command.memberCount,
            teamIntroduce = command.teamIntroduce,
        )
    }

    private fun validateCurrentUserIsLeader(meetingTeamId: UUID) {
        val leader = meetingTeamDomainService.getLeaderMemberByMeetingTeamId(meetingTeamId)
        val currentUser = getCurrentUserAuthentication()
        require(leader.userId == currentUser.userId) {
            "팀장만 팀 정보를 수정할 수 있어요!"
        }
    }

}
