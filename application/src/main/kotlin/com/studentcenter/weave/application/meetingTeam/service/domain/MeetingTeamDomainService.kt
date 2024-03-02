package com.studentcenter.weave.application.meetingTeam.service.domain

import com.studentcenter.weave.application.meetingTeam.vo.MeetingTeamListFilter
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.vo.MbtiAffinityScore
import java.util.*

interface MeetingTeamDomainService {

    fun save(meetingTeam: MeetingTeam)

    fun addMember(
        user: User,
        meetingTeam: MeetingTeam,
        role: MeetingMemberRole,
    ): MeetingMember

    fun updateById(
        id: UUID,
        location: Location? = null,
        memberCount: Int? = null,
        teamIntroduce: TeamIntroduce? = null,
    ): MeetingTeam

    fun deleteById(id: UUID)

    fun deleteMember(
        memberUserId: UUID,
        teamId: UUID
    )

    fun getById(id: UUID): MeetingTeam

    fun getByIdAndStatus(
        id: UUID,
        status: MeetingTeamStatus
    ): MeetingTeam

    // 멤버는 하나의 팀에만 소속될 수있음(MVP 기준)
    fun getByMemberUserId(userId: UUID): MeetingTeam

    fun findByMemberUserId(userId: UUID): MeetingTeam?

    /**
     * 팀 간 MBTI 궁합 점수를 계산해요
     * @return null - 팀간 MBTI 궁합 점수를 계산할 수 없는 경우
     */
    fun calculateTeamMbtiAffinityScore(
        meetingTeam: MeetingTeam,
        targetMeetingTeam: MeetingTeam
    ): MbtiAffinityScore?

    fun scrollByMemberUserId(
        userId: UUID,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam>

    fun scrollByFilter(
        filter: MeetingTeamListFilter,
        next: UUID?,
        limit: Int
    ): List<MeetingTeam>

    fun findAllMeetingMembersByMeetingTeamId(meetingTeamId: UUID): List<MeetingMember>

    fun getLeaderMemberByMeetingTeamId(meetingTeamId: UUID): MeetingMember

    fun getAllByIds(ids: List<UUID>): List<MeetingTeam>

}
