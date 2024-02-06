package com.studentcenter.weave.application.meeting.service.domain.impl

import com.studentcenter.weave.application.meeting.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meeting.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.Location
import com.studentcenter.weave.domain.meeting.vo.TeamIntroduce
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class MeetingTeamDomainServiceImpl(
    private val meetingTeamRepository: MeetingTeamRepository,
) : MeetingTeamDomainService {

    override fun create(
        teamIntroduce: TeamIntroduce,
        memberCount: Int,
        leaderUserId: UUID,
        location: Location
    ): MeetingTeam {
        return MeetingTeam.create(
            teamIntroduce = teamIntroduce,
            memberCount = memberCount,
            leaderUserId = leaderUserId,
            location = location
        ).also { meetingTeamRepository.save(it) }
    }

}
