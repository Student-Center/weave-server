package com.studentcenter.weave.bootstrap.meetingTeam.controller

import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamDeleteUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamEditUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetDetailUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetMyUseCase
import com.studentcenter.weave.bootstrap.meetingTeam.api.MeetingTeamApi
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamCreateRequest
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamEditRequest
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetDetailResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetLocationsResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetMyRequest
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetMyResponse
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class MeetingTeamRestController(
    private val meetingTeamCreateUseCase: MeetingTeamCreateUseCase,
    private val meetingTeamGetMyUseCase: MeetingTeamGetMyUseCase,
    private val meetingTeamDeleteUseCase: MeetingTeamDeleteUseCase,
    private val meetingTeamEditUseCase: MeetingTeamEditUseCase,
    private val meetingTeamGetDetailUseCase: MeetingTeamGetDetailUseCase,
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

    override fun getMeetingTeamLocations(): MeetingTeamGetLocationsResponse {
        return MeetingTeamGetLocationsResponse.getInstance()
    }

    override fun deleteMeetingTeam(id: UUID) {
        MeetingTeamDeleteUseCase.Command(id)
            .let { meetingTeamDeleteUseCase.invoke(it) }
    }

    override fun editMeetingTeam(
        id: UUID,
        request: MeetingTeamEditRequest
    ) {
        MeetingTeamEditUseCase.Command(
            id = id,
            location = request.location,
            memberCount = request.memberCount,
            teamIntroduce = request.teamIntroduce?.let(::TeamIntroduce),
        ).let { meetingTeamEditUseCase.invoke(it) }
    }

    override fun getMeetingTeamDetail(id: UUID): MeetingTeamGetDetailResponse {
        return MeetingTeamGetDetailUseCase.Command(id)
            .let { meetingTeamGetDetailUseCase.invoke(it) }
            .let { MeetingTeamGetDetailResponse.of(it.meetingTeam, it.members) }
    }

}
