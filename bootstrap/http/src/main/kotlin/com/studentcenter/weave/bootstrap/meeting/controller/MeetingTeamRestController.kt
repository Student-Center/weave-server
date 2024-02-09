package com.studentcenter.weave.bootstrap.meeting.controller

import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamGetMyUseCase
import com.studentcenter.weave.bootstrap.meeting.api.MeetingTeamApi
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamCreateRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamGetMyResponse
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.support.common.dto.ScrollRequest
import org.springframework.web.bind.annotation.RestController

@RestController
class MeetingTeamRestController(
    private val meetingTeamCreateUseCase: MeetingTeamCreateUseCase,
    private val meetingTeamGetMyUseCase: MeetingTeamGetMyUseCase,
) : MeetingTeamApi {

    override fun createMeetingTeam(request: MeetingTeamCreateRequest) {
        MeetingTeamCreateUseCase.Command(
            teamIntroduce = TeamIntroduce(request.teamIntroduce),
            memberCount = request.memberCount,
            location = request.location,
        ).let { meetingTeamCreateUseCase.invoke(it) }
    }

    override fun getMyMeetingTeams(scrollRequest: ScrollRequest): MeetingTeamGetMyResponse {
        return meetingTeamGetMyUseCase.invoke(scrollRequest).let {
            MeetingTeamGetMyResponse(
                item = it.item.map { meetingTeamInfo ->
                    MeetingTeamGetMyResponse.MeetingTeamDto.from(
                        meetingTeamInfo
                    )
                },
                lastItemId = it.lastItemId,
                limit = it.limit,
            )
        }
    }

}
