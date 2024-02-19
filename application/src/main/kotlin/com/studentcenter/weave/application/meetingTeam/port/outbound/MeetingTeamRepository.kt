package com.studentcenter.weave.application.meetingTeam.port.outbound

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.user.enums.Gender
import java.util.*

interface MeetingTeamRepository {

    fun save(meetingTeam: MeetingTeam)

    fun getById(id: UUID): MeetingTeam

    fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int,
    ): List<MeetingTeam>

    fun deleteById(id: UUID)

    fun scrollByFilter(
        memberCount: Int?,
        youngestMemberBirthYear: Int?,
        oldestMemberBirthYear: Int?,
        preferredLocations: List<Location>?,
        gender: Gender?,
        status: MeetingTeamStatus,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam>

}
