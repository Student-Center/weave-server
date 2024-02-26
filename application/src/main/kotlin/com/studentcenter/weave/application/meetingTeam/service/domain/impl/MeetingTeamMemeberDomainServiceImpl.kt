package com.studentcenter.weave.application.meetingTeam.service.domain.impl

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamMemberDomainService
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeetingTeamMemeberDomainServiceImpl(
    private val meetingMemberRepository: MeetingMemberRepository,
) : MeetingTeamMemberDomainService {

    override fun findAllByTeamIds(teamIds: List<UUID>): List<MeetingMember> {
        return meetingMemberRepository.findAllByMeetingTeamIds(teamIds)
    }

}
