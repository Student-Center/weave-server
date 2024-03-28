package com.studentcenter.weave.application.meetingTeam.service.application

import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeamMembers
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamMemberDomainService
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import org.springframework.stereotype.Service
import java.util.*

@Service
class GetMeetingTeamMemberQueryApplicationService(
    private val meetingTeamMemberDomainService: MeetingTeamMemberDomainService,
) : GetMeetingTeamMembers {

    override fun findAllByTeamIds(teamIds: List<UUID>): List<MeetingMember> {
        return meetingTeamMemberDomainService.findAllByTeamIds(teamIds)
    }

}
