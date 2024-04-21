package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.CancelAllMeeting
import com.studentcenter.weave.application.meetingTeam.port.inbound.DeleteMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.domain.meetingTeam.exception.MeetingTeamException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteMeetingTeamService(
    private val cancelAllMeeting: CancelAllMeeting,
    private val meetingTeamRepository: MeetingTeamRepository,
) : DeleteMeetingTeam {

    @Transactional
    override fun invoke(id: UUID) {
        val currentUserId: UUID = getCurrentUserAuthentication().userId

        val myTeam = meetingTeamRepository.getByMemberUserId(currentUserId)
        if (myTeam.id != id) {
            throw MeetingTeamException.IsNotTeamMember()
        }

        myTeam.delete(currentUserId, meetingTeamRepository::deleteById)
        cancelAllMeeting.invoke(CancelAllMeeting.Command(myTeam.id))
    }

}
