package com.studentcenter.weave.application.meetingTeam.service.domain

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamListFilter
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.User
import java.util.*

interface MeetingTeamDomainService {

    fun save(meetingTeam: MeetingTeam)

    fun addMember(
        user: User,
        meetingTeam: MeetingTeam,
        role: MeetingMemberRole,
    ): MeetingMember

    fun updateById(
        id: UUID,
        location: Location? = null,
        memberCount: Int? = null,
        teamIntroduce: TeamIntroduce? = null,
    ): MeetingTeam

    fun deleteById(id: UUID)

    fun deleteMember(
        memberUserId: UUID,
        teamId: UUID
    )

    fun publishById(id: UUID): MeetingTeam

    fun getById(id: UUID): MeetingTeam

    fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam>

    fun scrollByFilter(
        filter: MeetingTeamListFilter,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam>

    fun findAllMeetingMembersByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember>

    fun getLeaderMemberByMeetingTeamId(meetingTeamId: UUID): MeetingMember

}
