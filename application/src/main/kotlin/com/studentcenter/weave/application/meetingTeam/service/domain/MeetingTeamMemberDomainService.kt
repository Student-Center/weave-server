package com.studentcenter.weave.application.meetingTeam.service.domain

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import java.util.*

interface MeetingTeamMemberDomainService {

    fun findAllByTeamIds(teamIds: List<UUID>): List<MeetingMember>

}
