package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.FindMyRequestMeetingByReceivingTeamIdUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingAttendanceDomainService
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamQueryUseCase
import com.studentcenter.weave.domain.meeting.entity.Meeting
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class FindMyRequestMeetingByReceivingTeamIdApplicationService(
    private val meetingDomainService: MeetingDomainService,
    private val meetingTeamQueryUseCase: MeetingTeamQueryUseCase,
) : FindMyRequestMeetingByReceivingTeamIdUseCase {

    override fun invoke(receivingTeamId: UUID): Meeting? {
        val userId = getCurrentUserAuthentication().userId
        val myTeam = meetingTeamQueryUseCase.findByMemberUserId(userId)
            ?: throw NoSuchElementException("내가 속한 미팅팀이 없어요!")
        return meetingDomainService.findByRequestingTeamIdAndReceivingTeamId(
            requestingTeamId = myTeam.id,
            receivingTeamId = receivingTeamId,
        )
    }

}
