package com.studentcenter.weave.bootstrap.meeting.controller

import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamCreateUseCase
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamDeleteUseCase
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamEditUseCase
import com.studentcenter.weave.application.meeting.port.inbound.MeetingTeamGetMyUseCase
import com.studentcenter.weave.bootstrap.meeting.api.MeetingTeamApi

import com.studentcenter.weave.bootstrap.meeting.dto.*
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamCreateRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamEditRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamGetMyRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamGetMyResponse
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.domain.user.enums.Gender

import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class MeetingTeamRestController(
    private val meetingTeamCreateUseCase: MeetingTeamCreateUseCase,
    private val meetingTeamGetMyUseCase: MeetingTeamGetMyUseCase,
    private val meetingTeamDeleteUseCase: MeetingTeamDeleteUseCase,
    private val meetingTeamEditUseCase: MeetingTeamEditUseCase,
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
        return MeetingTeamGetDetailResponse(
            id = id,
            teamIntroduce = "팀 소개",
            memberCount = 3,
            location = Location.INCHON,
            gender = Gender.WOMAN,
            members = listOf(
                MeetingTeamGetDetailResponse.MeetingMemberDto(
                    id = UUID.randomUUID(),
                    universityName = "서울대학교",
                    mbti = "ENFP",
                    birthYear = 1998,
                    role = MeetingMemberRole.LEADER,
                    animalType = AnimalType.FOX,
                    height = 162,
                ),
                MeetingTeamGetDetailResponse.MeetingMemberDto(
                    id = UUID.randomUUID(),
                    universityName = "고려대학교",
                    mbti = "INTJ",
                    birthYear = 1999,
                    role = MeetingMemberRole.MEMBER,
                    animalType = null,
                    height = null,
                ),
                MeetingTeamGetDetailResponse.MeetingMemberDto(
                    id = UUID.randomUUID(),
                    universityName = "연세대학교",
                    mbti = "ENFJ",
                    birthYear = 1997,
                    role = MeetingMemberRole.MEMBER,
                    animalType = null,
                    height = 170,
                ),
            )
        )
    }

}
