package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.CancelAllMeetingUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.LeaveMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class LeaveMeetingTeamService(
    private val meetingTeamRepository: MeetingTeamRepository,
    private val cancelAllMeetingUseCase: CancelAllMeetingUseCase,
    private val getUser: GetUser,
) : LeaveMeetingTeam {

    @Transactional
    override fun invoke(id: UUID) {
        val user: User = getCurrentUserAuthentication()
            .userId
            .let { getUser.getById(it) }

        val meetingTeam = meetingTeamRepository.getById(id)

        if (meetingTeam.isPublished()) {
            cancelAllMeetingUseCase.invoke(CancelAllMeetingUseCase.Command(meetingTeam.id))
            meetingTeamRepository.deleteById(id)
        } else {
            meetingTeam
                .leaveMember(user)
                .also { meetingTeamRepository.save(it) }
        }
    }

}
