package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.exception.MeetingExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.FindMyRequestMeetingByReceivingTeamIdUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Service
import java.util.*

@Service
class FindMyRequestMeetingByReceivingTeamIdApplicationService(
    private val meetingDomainService: MeetingDomainService,
    private val getMeetingTeam: GetMeetingTeam,
) : FindMyRequestMeetingByReceivingTeamIdUseCase {

    override fun invoke(receivingTeamId: UUID): Meeting? {
        val userId = getCurrentUserAuthentication().userId
        val myTeam = getMeetingTeam.findByMemberUserId(userId).let {
            if (it == null || it.isPublished().not()) {
                throw CustomException(
                    MeetingExceptionType.NOT_FOUND_MY_MEETING_TEAM,
                    "내 미팅팀이 존재하지 않아요! 미팅팀에 참여해 주세요!",
                )
            }
            it
        }


        return meetingDomainService.findByRequestingTeamIdAndReceivingTeamId(
            requestingTeamId = myTeam.id,
            receivingTeamId = receivingTeamId,
        )
    }

}
