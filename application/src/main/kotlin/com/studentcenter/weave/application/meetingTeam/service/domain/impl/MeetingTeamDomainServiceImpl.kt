package com.studentcenter.weave.application.meetingTeam.service.domain.impl

import com.studentcenter.weave.application.common.exception.MeetingTeamExceptionType
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingMemberRepository
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamMemberSummaryRepository
import com.studentcenter.weave.application.meetingTeam.port.outbound.MeetingTeamRepository
import com.studentcenter.weave.application.meetingTeam.service.domain.MeetingTeamDomainService
import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamListFilter
import com.studentcenter.weave.application.user.port.inbound.GetUser
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.vo.MbtiAffinityScore
import com.studentcenter.weave.domain.user.vo.MbtiAffinityScore.Companion.getAffinityScore
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.lock.distributedLock
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MeetingTeamDomainServiceImpl(
    private val meetingTeamRepository: MeetingTeamRepository,
    private val meetingMemberRepository: MeetingMemberRepository,
    private val meetingTeamMemberSummaryRepository: MeetingTeamMemberSummaryRepository,
    private val getUser: GetUser,
) : MeetingTeamDomainService {
    override fun save(meetingTeam: MeetingTeam) {
        meetingTeamRepository.save(meetingTeam)
    }

    @Transactional(readOnly = true)
    override fun getById(id: UUID): MeetingTeam {
        return meetingTeamRepository.getById(id)
    }

    override fun getByIdAndStatus(
        id: UUID,
        status: MeetingTeamStatus
    ): MeetingTeam {
        return meetingTeamRepository.getByIdAndStatus(id, status)
    }

    override fun getByMemberUserId(userId: UUID): MeetingTeam {
        return meetingTeamRepository.getByMemberUserId(userId)
    }

    override fun findByMemberUserId(userId: UUID): MeetingTeam? {
        return meetingTeamRepository.findByMemberUserId(userId)
    }

    override fun calculateTeamMbtiAffinityScore(
        meetingTeam: MeetingTeam,
        targetMeetingTeam: MeetingTeam
    ): MbtiAffinityScore? {
        if (meetingTeam.status != MeetingTeamStatus.PUBLISHED || targetMeetingTeam.status != MeetingTeamStatus.PUBLISHED) {
            return null
        }
        if (meetingTeam.id == targetMeetingTeam.id) {
            return null
        }
        val mbti = meetingTeamMemberSummaryRepository
            .getByMeetingTeamId(meetingTeam.id)
            .teamMbti
        val targetMbti = meetingTeamMemberSummaryRepository
            .getByMeetingTeamId(targetMeetingTeam.id)
            .teamMbti
        return mbti.getAffinityScore(targetMbti)
    }

    @Transactional(readOnly = true)
    override fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam> {
        return meetingTeamRepository.scrollByMemberUserId(userId, next, limit)
    }

    @Transactional(readOnly = true)
    override fun scrollByFilter(
        filter: MeetingTeamListFilter,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam> {
        return meetingTeamRepository.scrollByFilter(
            filter = filter,
            next = next,
            limit = limit,
        )
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

    @Transactional(readOnly = true)
    override fun getAllByIds(ids: List<UUID>): List<MeetingTeam> {
        val meetingTeams = meetingTeamRepository.findAllById(ids)
        require(meetingTeams.size == ids.size) { "미팅 팀을 찾을 수 없습니다." }
        return meetingTeams
    }

    override fun addMember(
        user: User,
        meetingTeam: MeetingTeam,
        role: MeetingMemberRole,
    ): MeetingMember = distributedLock("${this::addMember.name}:${meetingTeam.id}") {
        checkMemberCount(meetingTeam)

        meetingMemberRepository.findByUserId(user.id)?.also {
            throw CustomException(
                type = MeetingTeamExceptionType.ALREADY_JOINED_MEMBER,
                message = "이미 미팅 팀에 소속되어 있는 멤버에요!"
            )
        }

        return@distributedLock MeetingMember.create(
            meetingTeamId = meetingTeam.id,
            userId = user.id,
            role = role,
        ).also { newMember ->
            meetingMemberRepository.save(newMember)
            publishTeamIfNeeded(
                meetingTeam,
                meetingMemberRepository.countByMeetingTeamId(meetingTeam.id)
            )
        }

    }

    @Transactional
    override fun updateById(
        id: UUID,
        location: Location?,
        memberCount: Int?,
        teamIntroduce: TeamIntroduce?,
    ): MeetingTeam {
        val currentMemberCount = meetingMemberRepository.countByMeetingTeamId(id)
        memberCount?.let { checkMemberCountUpdatable(currentMemberCount, it) }
        return meetingTeamRepository
            .getById(id)
            .update(teamIntroduce, memberCount, location)
            .also {
                meetingTeamRepository.save(it)
                publishTeamIfNeeded(it, currentMemberCount)
            }
    }

    @Transactional
    override fun deleteById(id: UUID) {
        meetingMemberRepository.deleteAllByMeetingTeamId(id)
        meetingTeamRepository.deleteById(id)
    }

    @Transactional
    override fun deleteMember(
        memberUserId: UUID,
        teamId: UUID
    ) {
        getTeamMember(teamId, memberUserId)
            .also {
                verifyIsNotTeamLeader(it)
                meetingMemberRepository.deleteById(it.id)
            }
    }

    override fun getMeetingTeamMemberSummaryByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary {
        return meetingTeamMemberSummaryRepository.getByMeetingTeamId(meetingTeamId)
    }

    private fun publishTeamIfNeeded(meetingTeam: MeetingTeam, currentMemberCount: Int) {
        if (meetingTeam.memberCount == currentMemberCount && meetingTeam.isPublished().not()) {
            createMeetingTeamMemberSummary(meetingTeam)
                .also { meetingTeamMemberSummaryRepository.save(it) }

            meetingTeam.publish()
                .also { meetingTeamRepository.save(it) }
        }
    }

    private fun getTeamMember(
        teamId: UUID,
        userId: UUID
    ): MeetingMember {
        return meetingMemberRepository
            .findByMeetingTeamIdAndUserId(teamId, userId)
            ?: throw CustomException(
                type = MeetingTeamExceptionType.IS_NOT_TEAM_MEMBER,
                message = "미팅 팀 멤버가 아니에요!"
            )
    }

    private fun verifyIsNotTeamLeader(meetingMember: MeetingMember) {
        if (meetingMember.role == MeetingMemberRole.LEADER) {
            throw CustomException(
                type = MeetingTeamExceptionType.LEADER_CANNOT_LEAVE_TEAM,
                message = "팀장은 팀을 나갈 수 없어요! - 팀을 삭제해주세요!"
            )
        }
    }

    private fun checkMemberCountUpdatable(
        currentMemberCount: Int,
        updateMemberCount: Int,
    ) {
        require(currentMemberCount <= updateMemberCount) {
            "이미 참여한 팀원 수보다 적은 수로 업데이트 할 수 없어요!"
        }
    }

    private fun checkMemberCount(meetingTeam: MeetingTeam) {
        require(meetingMemberRepository.countByMeetingTeamId(meetingTeam.id) < meetingTeam.memberCount) {
            "팀원의 수가 초과되었습니다"
        }
    }

    private fun createMeetingTeamMemberSummary(meetingTeam: MeetingTeam): MeetingTeamMemberSummary {
        val meetingMemberUsers: List<User> = meetingMemberRepository
            .findAllByMeetingTeamId(meetingTeam.id)
            .map { getUser.getById(it.userId) }
        require(meetingMemberUsers.size == meetingTeam.memberCount) {
            "설정된 팀원의 수와 실제 팀원의 수가 일치해야 팀을 공개할 수 있어요!"
        }
        return MeetingTeamMemberSummary.create(
            meetingTeamId = meetingTeam.id,
            members = meetingMemberUsers
        )
    }

}
