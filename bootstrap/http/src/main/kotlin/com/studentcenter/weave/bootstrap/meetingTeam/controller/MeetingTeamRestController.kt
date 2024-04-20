package com.studentcenter.weave.bootstrap.meetingTeam.controller

import com.studentcenter.weave.application.meetingTeam.port.inbound.CreateInvitationLink
import com.studentcenter.weave.application.meetingTeam.port.inbound.CreateMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.inbound.DeleteMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.inbound.EditMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetListMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeamByInvitationCode
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeamDetail
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMyMeetingTeam
import com.studentcenter.weave.application.meetingTeam.port.inbound.JoinMeetingTeam
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
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamMemberDetailResponse
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class MeetingTeamRestController(
    private val createMeetingTeam: CreateMeetingTeam,
    private val getMyMeetingTeam: GetMyMeetingTeam,
    private val deleteMeetingTeam: DeleteMeetingTeam,
    private val editMeetingTeam: EditMeetingTeam,
    private val getMeetingTeamDetail: GetMeetingTeamDetail,
    private val leaveMeetingTeam: LeaveMeetingTeam,
    private val getListMeetingTeam: GetListMeetingTeam,
    private val createInvitationLink: CreateInvitationLink,
    private val getMeetingTeamByInvitationCodeUseCase: GetMeetingTeamByInvitationCode,
    private val joinMeetingTeam: JoinMeetingTeam,
    private val getMeetingTeam: GetMeetingTeam,
) : MeetingTeamApi {

    override fun createMeetingTeam(request: MeetingTeamCreateRequest) {
        CreateMeetingTeam.Command(
            teamIntroduce = TeamIntroduce(request.teamIntroduce),
            memberCount = request.memberCount,
            location = request.location,
        ).let { createMeetingTeam.invoke(it) }
    }

    override fun getMyMeetingTeams(request: MeetingTeamGetMyRequest): MeetingTeamGetMyResponse {
        return GetMyMeetingTeam.Command(
            next = request.next,
            limit = request.limit,
        ).let {
            getMyMeetingTeam.invoke(it)
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
        deleteMeetingTeam.invoke(id)
    }

    override fun editMeetingTeam(
        id: UUID,
        request: MeetingTeamEditRequest,
    ) {
        EditMeetingTeam.Command(
            id = id,
            location = request.location,
            memberCount = request.memberCount,
            teamIntroduce = request.teamIntroduce?.let(::TeamIntroduce),
        ).let { editMeetingTeam.invoke(it) }
    }

    override fun getMeetingTeamDetail(id: UUID): MeetingTeamGetDetailResponse {
        return GetMeetingTeamDetail.Command(id)
            .let { getMeetingTeamDetail.invoke(it) }
            .let { MeetingTeamGetDetailResponse.of(it.meetingTeam, it.members, it.affinityScore) }
    }

    override fun getMeetingTeams(request: MeetingTeamGetListRequest): MeetingTeamGetListResponse {
        return request.toQuery()
            .let { getListMeetingTeam.invoke(it) }
            .let {
                MeetingTeamGetListResponse(
                    items = it.items.map { item ->
                        MeetingTeamGetListResponse.MeetingTeamDto.from(item)
                    },
                    next = it.next,
                    total = it.total
                )
            }
    }

    override fun leaveMeetingTeam(id: UUID) {
        leaveMeetingTeam.invoke(id)
    }

    override fun createMeetingTeamInvitation(meetingTeamId: UUID): MeetingTeamCreateInvitationResponse {
        val result = createInvitationLink.invoke(meetingTeamId)

        return MeetingTeamCreateInvitationResponse(
            meetingTeamInvitationLink = result.meetingTeamInvitationLink,
            meetingTeamInvitationCode = result.meetingTeamInvitationCode,
        )
    }

    override fun getMeetingTeamByInvitationCode(invitationCode: UUID): MeetingTeamGetByInvitationCodeResponse {
        return getMeetingTeamByInvitationCodeUseCase.invoke(invitationCode)
            .let { MeetingTeamGetByInvitationCodeResponse.of(it) }
    }

    override fun joinMeetingTeam(invitationCode: UUID) {
        joinMeetingTeam.invoke(invitationCode)
    }

    override fun getMeetingTeamMemberDetail(
        teamId: UUID,
        memberId: UUID,
    ): MeetingTeamMemberDetailResponse {
        return getMeetingTeam
            .getMemberDetail(teamId, memberId)
            .let { MeetingTeamMemberDetailResponse.from(it) }

    }

}
