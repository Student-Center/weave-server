package com.studentcenter.weave.application.meeting.service.domain.impl

import com.studentcenter.weave.application.meeting.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.application.meeting.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meeting.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.domain.meeting.entity.MeetingMember
import com.studentcenter.weave.domain.meeting.entity.MeetingTeam
import com.studentcenter.weave.domain.meeting.enums.MeetingMemberRole
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MeetingTeamDomainServiceImpl(
    private val meetingTeamRepository: MeetingTeamRepository,
    private val meetingMemberRepository: MeetingMemberRepository,
) : MeetingTeamDomainService {

    override fun save(meetingTeam: MeetingTeam) {
        meetingTeamRepository.save(meetingTeam)
    }

    override fun getById(id: UUID): MeetingTeam {
        return meetingTeamRepository.getById(id)
    }

    @Transactional
    override fun addMember(
        user: User,
        meetingTeam: MeetingTeam,
        role: MeetingMemberRole,
    ): MeetingMember {
        require(meetingMemberRepository.countByMeetingTeamId(meetingTeam.id) < meetingTeam.memberCount) {
            "팀원의 수가 초과되었습니다"
        }

        return MeetingMember.create(
            meetingTeamId = meetingTeam.id,
            userId = user.id,
            role = role,
        ).also {
            meetingMemberRepository.save(it)
        }
    }

}
