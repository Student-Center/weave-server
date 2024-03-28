package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.EditMeetingTeam
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class EditApplicationService(
    private val meetingTeamDomainService: MeetingTeamDomainService,
) : EditMeetingTeam {

    @Transactional
    override fun invoke(command: EditMeetingTeam.Command) {
        meetingTeamDomainService
            .getById(command.id)
            .also {
                validateMeetingTeamNotPublished(it)
                validateCurrentUserIsLeader(it.id)
                updateMeetingTeam(it.id, command)
            }
    }

    private fun updateMeetingTeam(
        meetingTeamId: UUID,
        command: EditMeetingTeam.Command
    ) {
        meetingTeamDomainService.updateById(
            id = meetingTeamId,
            location = command.location,
            memberCount = command.memberCount,
            teamIntroduce = command.teamIntroduce,
        )
    }

    private fun validateMeetingTeamNotPublished(meetingTeam: MeetingTeam) {
        require(meetingTeam.status != MeetingTeamStatus.PUBLISHED) {
            "이미 공개된 팀 정보는 수정할 수 없어요!"
        }
    }

    private fun validateCurrentUserIsLeader(meetingTeamId: UUID) {
        val leader = meetingTeamDomainService.getLeaderMemberByMeetingTeamId(meetingTeamId)
        val currentUser = getCurrentUserAuthentication()
        require(leader.userId == currentUser.userId) {
            "팀장만 팀 정보를 수정할 수 있어요!"
        }
    }

}
