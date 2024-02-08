package com.studentcenter.weave.application.meeting.service.domain.impl

import com.studentcenter.weave.application.meeting.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meeting.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class MeetingTeamDomainServiceImpl(
    private val meetingTeamRepository: MeetingTeamRepository,
) : MeetingTeamDomainService {

    override fun create(
        teamIntroduce: TeamIntroduce,
        memberCount: Int,
        leaderUser: User,
        location: Location
    ): MeetingTeam {
        return MeetingTeam.create(
            teamIntroduce = teamIntroduce,
            memberCount = memberCount,
            leaderUser = leaderUser,
            location = location
        ).also { meetingTeamRepository.save(it) }
    }

}
