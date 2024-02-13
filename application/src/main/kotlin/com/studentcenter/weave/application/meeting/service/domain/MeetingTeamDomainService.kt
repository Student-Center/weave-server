package com.studentcenter.weave.application.meeting.service.domain

import com.studentcenter.weave.domain.meeting.entity.MeetingMember
import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.User
import java.util.*

interface MeetingTeamDomainService {

    fun save(meetingTeam: MeetingTeam)

    fun getById(id: UUID): MeetingTeam

    fun addMember(
        user: User,
        meetingTeam: MeetingTeam,
        role: MeetingMemberRole,
    ): MeetingMember

}
