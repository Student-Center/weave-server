package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meetingTeam.port.inbound.EditMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EditMeetingTeamService(
    private val meetingTeamRepository: MeetingTeamRepository,
) : EditMeetingTeam {

    @Transactional
    override fun invoke(command: EditMeetingTeam.Command) {
        meetingTeamRepository
            .getById(command.id)
            .update(
                triggerUserId = getCurrentUserAuthentication().userId,
                location = command.location,
                memberCount = command.memberCount,
                teamIntroduce = command.teamIntroduce,
            ).also { meetingTeamRepository.save(it) }
    }

}
