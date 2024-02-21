package com.studentcenter.weave.application.meetingTeam.port.outbound

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamListFilter
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import java.util.*

interface MeetingTeamRepository {

    fun save(meetingTeam: MeetingTeam)

    fun getById(id: UUID): MeetingTeam

    // 멤버는 하나의 팀에만 소속될 수있음(MVP 기준)
    fun getByMemberUserId(userId: UUID): MeetingTeam

    fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int,
    ): List<MeetingTeam>

    fun deleteById(id: UUID)

    fun scrollByFilter(
        filter: MeetingTeamListFilter,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam>

}
