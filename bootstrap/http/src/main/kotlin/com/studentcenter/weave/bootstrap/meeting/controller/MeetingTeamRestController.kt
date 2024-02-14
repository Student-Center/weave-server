package com.studentcenter.weave.bootstrap.meeting.controller

import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamDeleteUseCase
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamGetMyUseCase
import com.studentcenter.weave.bootstrap.meeting.api.MeetingTeamApi
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamCreateRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamGetMyRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamGetMyResponse
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamLocationResponse
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class MeetingTeamRestController(
    private val meetingTeamCreateUseCase: MeetingTeamCreateUseCase,
    private val meetingTeamGetMyUseCase: MeetingTeamGetMyUseCase,
    private val meetingTeamDeleteUseCase: MeetingTeamDeleteUseCase,
) : MeetingTeamApi {

    override fun createMeetingTeam(request: MeetingTeamCreateRequest) {
        MeetingTeamCreateUseCase.Command(
            teamIntroduce = TeamIntroduce(request.teamIntroduce),
            memberCount = request.memberCount,
            location = request.location,
        ).let { meetingTeamCreateUseCase.invoke(it) }
    }

    override fun getMyMeetingTeams(request: MeetingTeamGetMyRequest): MeetingTeamGetMyResponse {
        return MeetingTeamGetMyUseCase.Command(
            next = request.next,
            limit = request.limit,
        ).let {
            meetingTeamGetMyUseCase.invoke(it)
        }.let {
            MeetingTeamGetMyResponse(
                item = it.item.map { item -> MeetingTeamGetMyResponse.MeetingTeamDto.from(item) },
                next = it.next,
                total = it.total
            )
        }
    }

    override fun getMeetingTeamLocations(): MeetingTeamLocationResponse {
        return MeetingTeamLocationResponse.getInstance()
    }

    override fun deleteMeetingTeam(id: UUID) {
        MeetingTeamDeleteUseCase.Command(id)
            .let { meetingTeamDeleteUseCase.invoke(it) }
    }

}
