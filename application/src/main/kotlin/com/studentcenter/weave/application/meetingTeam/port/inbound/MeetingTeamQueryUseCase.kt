package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import java.util.*

interface MeetingTeamQueryUseCase {

    fun getById(meetingTeamId: UUID): MeetingTeam

    fun getByIdAndStatus(
        meetingTeamId: UUID,
        status: MeetingTeamStatus
    ): MeetingTeam

    fun getByMemberUserId(memberUserId: UUID): MeetingTeam

    fun findByMemberUserId(userId: UUID): MeetingTeam?

    fun findAllMeetingMembersByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember>

}