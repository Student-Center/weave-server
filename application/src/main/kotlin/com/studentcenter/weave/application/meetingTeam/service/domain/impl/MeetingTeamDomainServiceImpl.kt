package com.studentcenter.weave.application.meetingTeam.service.domain.impl

import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
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

    override fun scrollByFilter(
        memberCount: Int?,
        minBirthYear: Int?,
        maxBirthYear: Int?,
        preferredLocations: List<Location>?,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam> {
        TODO("Not yet implemented")
    }

    override fun findAllMeetingMembersByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember> {
        return meetingMemberRepository.findAllByMeetingTeamId(meetingTeamId)
    }

    @Transactional(readOnly = true)
    override fun getLeaderMemberByMeetingTeamId(meetingTeamId: UUID): MeetingMember {
        return meetingMemberRepository
            .findAllByMeetingTeamId(meetingTeamId)
            .firstOrNull { it.role == MeetingMemberRole.LEADER }
            ?: throw NoSuchElementException("미팅팀 팀장을 찾을 수 없어요!")
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

    override fun updateById(
        id: UUID,
        location: Location?,
        memberCount: Int?,
        teamIntroduce: TeamIntroduce?,
    ): MeetingTeam {
        memberCount?.let { checkMemberCountUpdatable(id, it) }

        return meetingTeamRepository
            .getById(id)
            .update(teamIntroduce, memberCount, location)
            .also { meetingTeamRepository.save(it) }
    }

    private fun checkMemberCountUpdatable(
        meetingTeamId: UUID,
        memberCount: Int,
    ) {
        require(meetingMemberRepository.findAllByMeetingTeamId(meetingTeamId).size < memberCount) {
            "이미 참여한 팀원 수보다 적은 수로 업데이트 할 수 없어요!"
        }
    }

    @Transactional
    override fun deleteById(id: UUID) {
        meetingMemberRepository.deleteAllByMeetingTeamId(id)
        meetingTeamRepository.deleteById(id)
    }

    private fun checkMemberCount(meetingTeam: MeetingTeam) {
        require(meetingMemberRepository.countByMeetingTeamId(meetingTeam.id) < meetingTeam.memberCount) {
            "팀원의 수가 초과되었습니다"
        }
    }

}
