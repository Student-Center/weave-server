package com.studentcenter.weave.application.meeting.service.domain

import com.studentcenter.weave.domain.meeting.entity.MeetingMember
import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
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

    fun getById(id: UUID): MeetingTeam

    fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam>

    fun findAllMeetingMembersByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember>

    fun getLeaderMemberByMeetingTeamId(meetingTeamId: UUID): MeetingMember

}
