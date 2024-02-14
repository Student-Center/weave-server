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

    override fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam> {
        return meetingTeamRepository.scrollByMemberUserId(userId, next, limit)
    }

    override fun findAllMeetingMembersByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember> {
        return meetingMemberRepository.findAllByMeetingTeamId(meetingTeamId)
    }

    @Transactional
    override fun addMember(
        user: User,
        meetingTeam: MeetingTeam,
        role: MeetingMemberRole,
    ): MeetingMember {
        // TODO: 동시성 문제 해결
        checkMemberCount(meetingTeam)
        val existingMember = meetingMemberRepository.findByMeetingTeamIdAndUserId(
            meetingTeam.id,
            user.id
        )

        return existingMember ?: run {
            MeetingMember.create(
                meetingTeamId = meetingTeam.id,
                userId = user.id,
                role = role,
            ).also {
                meetingMemberRepository.save(it)
            }
        }
    }

    private fun checkMemberCount(meetingTeam: MeetingTeam) {
        require(meetingMemberRepository.countByMeetingTeamId(meetingTeam.id) < meetingTeam.memberCount) {
            "팀원의 수가 초과되었습니다"
        }
    }

}
