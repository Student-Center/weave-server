package com.studentcenter.weave.bootstrap.meeting.controller

import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.bootstrap.meeting.api.MeetingTeamApi
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamCreateRequest
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import org.springframework.web.bind.annotation.RestController

@RestController
class MeetingTeamRestController (
    private val meetingTeamCreateUseCase: MeetingTeamCreateUseCase,
): MeetingTeamApi {

    override fun createMeetingTeam(request: MeetingTeamCreateRequest) {
        MeetingTeamCreateUseCase.Command(
            teamIntroduce = TeamIntroduce(request.teamIntroduce),
            memberCount = request.memberCount,
            location = request.location,
        ).let { meetingTeamCreateUseCase.invoke(it) }
    }

}
