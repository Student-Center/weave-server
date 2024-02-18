package com.studentcenter.weave.bootstrap.meetingTeam.controller

import com.studentcenter.weave.application.meetingTeam.port.inbound.*
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamDeleteUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamEditUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetDetailUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamGetMyUseCase
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamLeaveUseCase
import com.studentcenter.weave.bootstrap.meetingTeam.api.MeetingTeamApi
import com.studentcenter.weave.bootstrap.meetingTeam.dto.*
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
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
    private val meetingTeamCreateInvitationUseCase: MeetingTeamCreateInvitationUseCase,
    private val meetingTeamLeaveUseCase: MeetingTeamLeaveUseCase,
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

    override fun getMeetingTeams(request: MeetingTeamGetListRequest): MeetingTeamGetListResponse {
        return MeetingTeamGetListResponse(
            items = listOf(
                MeetingTeamGetListResponse.MeetingTeamDto(
                    id = UUID.randomUUID(),
                    teamIntroduce = "팀 소개1",
                    memberCount = 3,
                    location = "서울",
                    memberInfos = listOf(
                        MeetingTeamGetListResponse.MeetingMemberDto(
                            id = UUID.randomUUID(),
                            universityName = "서울대학교",
                            mbti = "ENFP",
                            birthYear = 1998,
                            role = MeetingMemberRole.LEADER,
                        ),
                        MeetingTeamGetListResponse.MeetingMemberDto(
                            id = UUID.randomUUID(),
                            universityName = "연세대학교",
                            mbti = "INTJ",
                            birthYear = 1999,
                            role = MeetingMemberRole.MEMBER,
                        ),
                        MeetingTeamGetListResponse.MeetingMemberDto(
                            id = UUID.randomUUID(),
                            universityName = "고려대학교",
                            mbti = "ENFJ",
                            birthYear = 1997,
                            role = MeetingMemberRole.MEMBER,
                        ),
                    ),
                ),
                MeetingTeamGetListResponse.MeetingTeamDto(
                    id = UUID.randomUUID(),
                    teamIntroduce = "팀 소개2",
                    memberCount = 3,
                    location = "서울",
                    memberInfos = listOf(
                        MeetingTeamGetListResponse.MeetingMemberDto(
                            id = UUID.randomUUID(),
                            universityName = "서울대학교",
                            mbti = "ENFP",
                            birthYear = 2000,
                            role = MeetingMemberRole.LEADER,
                        ),
                        MeetingTeamGetListResponse.MeetingMemberDto(
                            id = UUID.randomUUID(),
                            universityName = "연세대학교",
                            mbti = "INTJ",
                            birthYear = 1999,
                            role = MeetingMemberRole.MEMBER,
                        ),
                        MeetingTeamGetListResponse.MeetingMemberDto(
                            id = UUID.randomUUID(),
                            universityName = "고려대학교",
                            mbti = "ENFJ",
                            birthYear = 1997,
                            role = MeetingMemberRole.MEMBER,
                        ),
                    ),
                ),
            ),
            next = UUID.randomUUID(),
            total = 2,
        )
    }

    override fun createMeetingTeamInvitation(meetingTeamId: UUID): MeetingTeamCreateInvitationResponse {
        return MeetingTeamCreateInvitationUseCase.Command(
            meetingTeamId = meetingTeamId
        ).let {
            meetingTeamCreateInvitationUseCase.invoke(it)
        }.let {
            MeetingTeamCreateInvitationResponse(
                teamId = it.teamId,
                invitationCode = it.invitationCode
            )
        }

    override fun leaveMeetingTeam(id: UUID) {
        meetingTeamLeaveUseCase.invoke(MeetingTeamLeaveUseCase.Command(id))
    }

}
