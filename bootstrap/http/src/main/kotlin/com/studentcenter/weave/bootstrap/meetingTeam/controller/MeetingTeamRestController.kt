package com.studentcenter.weave.bootstrap.meetingTeam.controller

import com.studentcenter.weave.application.meetingTeam.port.inbound.CreateInvitationLink
import com.studentcenter.weave.application.meetingTeam.port.inbound.CreateMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.inbound.DeleteMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.inbound.EditMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.inbound.EnterMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetByInvitationCodeUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetDetailUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetListUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetMyUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.LeaveMeetingTeam
import com.studentcenter.weave.bootstrap.meetingTeam.api.MeetingTeamApi
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamCreateInvitationResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamCreateRequest
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamEditRequest
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetByInvitationCodeResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetDetailResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetListRequest
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetListResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetLocationsResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetMyRequest
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetMyResponse
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class MeetingTeamRestController(
    private val meetingTeamCreateUseCase: CreateMeetingTeam,
    private val meetingTeamGetMyUseCase: MeetingTeamGetMyUseCase,
    private val meetingTeamDeleteUseCase: DeleteMeetingTeam,
    private val meetingTeamEditUseCase: EditMeetingTeam,
    private val meetingTeamGetDetailUseCase: MeetingTeamGetDetailUseCase,
    private val meetingTeamLeaveUseCase: LeaveMeetingTeam,
    private val meetingTeamGetListUseCase: MeetingTeamGetListUseCase,
    private val createInvitationLink: CreateInvitationLink,
    private val meetingTeamGetByInvitationCodeUseCase: MeetingTeamGetByInvitationCodeUseCase,
    private val meetingTeamEnterUseCase: EnterMeetingTeam,
) : MeetingTeamApi {

    override fun createMeetingTeam(request: MeetingTeamCreateRequest) {
        CreateMeetingTeam.Command(
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
                items = it.items.map { item -> MeetingTeamGetMyResponse.MeetingTeamDto.from(item) },
                next = it.next,
                total = it.total
            )
        }
    }

    override fun getMeetingTeamLocations(): MeetingTeamGetLocationsResponse {
        return MeetingTeamGetLocationsResponse.getInstance()
    }

    override fun deleteMeetingTeam(id: UUID) {
        meetingTeamDeleteUseCase.invoke(id)
    }

    override fun editMeetingTeam(
        id: UUID,
        request: MeetingTeamEditRequest
    ) {
        EditMeetingTeam.Command(
            id = id,
            location = request.location,
            memberCount = request.memberCount,
            teamIntroduce = request.teamIntroduce?.let(::TeamIntroduce),
        ).let { meetingTeamEditUseCase.invoke(it) }
    }

    override fun getMeetingTeamDetail(id: UUID): MeetingTeamGetDetailResponse {
        return MeetingTeamGetDetailUseCase.Command(id)
            .let { meetingTeamGetDetailUseCase.invoke(it) }
            .let { MeetingTeamGetDetailResponse.of(it.meetingTeam, it.members, it.affinityScore) }
    }

    override fun getMeetingTeams(request: MeetingTeamGetListRequest): MeetingTeamGetListResponse {
        return MeetingTeamGetListUseCase.Command(
            memberCount = request.memberCount,
            youngestMemberBirthYear = request.youngestMemberBirthYear,
            oldestMemberBirthYear = request.oldestMemberBirthYear,
            preferredLocations = request.preferredLocations,
            next = request.next,
            limit = request.limit
        ).let {
            meetingTeamGetListUseCase.invoke(it)
        }.let {
            MeetingTeamGetListResponse(
                items = it.items.map { item -> MeetingTeamGetListResponse.MeetingTeamDto.from(item) },
                next = it.next,
                total = it.total
            )
        }
    }

    override fun leaveMeetingTeam(id: UUID) {
        meetingTeamLeaveUseCase.invoke(LeaveMeetingTeam.Command(id))
    }

    override fun createMeetingTeamInvitation(meetingTeamId: UUID): MeetingTeamCreateInvitationResponse {
        val result = createInvitationLink.invoke(meetingTeamId)

        return MeetingTeamCreateInvitationResponse(
            meetingTeamInvitationLink = result.meetingTeamInvitationLink,
            meetingTeamInvitationCode = result.meetingTeamInvitationCode,
        )
    }

    override fun getMeetingTeamByInvitationCode(invitationCode: UUID): MeetingTeamGetByInvitationCodeResponse {
        return meetingTeamGetByInvitationCodeUseCase.invoke(invitationCode)
            .let { MeetingTeamGetByInvitationCodeResponse.of(it) }
    }

    override fun enterMeetingTeam(invitationCode: UUID) {
        meetingTeamEnterUseCase.invoke(invitationCode)
    }

}
