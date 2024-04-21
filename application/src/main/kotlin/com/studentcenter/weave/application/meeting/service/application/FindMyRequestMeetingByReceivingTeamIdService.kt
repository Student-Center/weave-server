package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.FindMyRequestMeetingByReceivingTeamId
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meetingTeam.exception.MeetingTeamException
import org.springframework.stereotype.Service
import java.util.*

@Service
class FindMyRequestMeetingByReceivingTeamIdService(
    private val meetingDomainService: MeetingDomainService,
    private val getMeetingTeam: GetMeetingTeam,
) : FindMyRequestMeetingByReceivingTeamId {

    override fun invoke(receivingTeamId: UUID): Meeting? {
        val userId = getCurrentUserAuthentication().userId
        val myTeam = getMeetingTeam
            .findByMemberUserId(userId)
            .let {
                if (it == null || it.isPublished().not()) {
                    throw MeetingTeamException.CanNotFindMyMeetingTeam()
                }
                it
            }

        return meetingDomainService.findByRequestingTeamIdAndReceivingTeamId(
            requestingTeamId = myTeam.id,
            receivingTeamId = receivingTeamId,
        )
    }

}
