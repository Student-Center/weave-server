package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.*

data class MeetingTeam(
    val id: UUID = UuidCreator.create(),
    val teamIntroduce: TeamIntroduce,
    val memberCount: Int,
    val members: List<MeetingMember>,
    val location: Location,
    val status: MeetingTeamStatus,
    val gender: Gender,
) {

    val leader: MeetingMember
        get() = members.first { it.role == MeetingMemberRole.LEADER }

    init {
        require(memberCount in 2..4) {
            "미팅할 팀원의 수는 최소 2명에서 최대 4명까지 가능해요"
        }
    }

    fun isMyTeam(userId: UUID): Boolean {
        return members.any { it.userId == userId }
    }

    fun joinMember(user: User): MeetingTeam {
        require(status != MeetingTeamStatus.PUBLISHED) {
            "팀이 공개된 상태에서는 팀에 가입할 수 없어요."
        }
        require(members.size <= memberCount) {
            "팀이 꽉 찼어요! 더 이상 팀원을 추가할 수 없어요."
        }
        require(members.none { it.userId == user.id }) {
            "이미 팀에 가입한 유저입니다."
        }

        val meetingMember: MeetingMember =
            MeetingMember.create(userId = user.id, role = MeetingMemberRole.MEMBER)
        val result: MeetingTeam = copy(members = members + meetingMember)
        return if (result.members.size == memberCount) result.publish() else result
    }

    fun leaveMember(user: User): MeetingTeam {
        require(status != MeetingTeamStatus.PUBLISHED) {
            "팀이 공개된 상태에서는 팀을 나갈 수 없어요."
        }
        require(members.any { it.userId == user.id }) {
            "팀에 가입하지 않은 유저입니다."
        }
        require(members.find { it.userId == user.id }?.role != MeetingMemberRole.LEADER) {
            "팀장은 팀을 탈퇴할 수 없어요."
        }

        return copy(members = members.filter { it.userId != user.id })
    }

    fun validateInvitable(triggerUserId: UUID) {
        require(leader.userId == triggerUserId) {
            "팀장만 새로운 팀원을 초대할 수 있어요!"
        }

        require(status != MeetingTeamStatus.PUBLISHED) {
            "팀이 공개된 상태에서는 초대 링크를 발급할 수 없어요."
        }

        require(members.size < memberCount) {
            "팀이 꽉 찼어요! 더 이상 팀원을 추가할 수 없어요."
        }
    }

    fun delete(
        triggerUserId: UUID,
        deleteByIdAction: (UUID) -> Unit,
    ) {
        require(status != MeetingTeamStatus.PUBLISHED) {
            "팀이 공개된 상태에서는 팀을 삭제할 수 없어요."
        }
        require(leader.userId == triggerUserId) {
            "팀장만 팀을 삭제할 수 있어요."
        }

        deleteByIdAction(id)
    }

    fun update(
        triggerUserId: UUID,
        teamIntroduce: TeamIntroduce?,
        memberCount: Int?,
        location: Location?,
    ): MeetingTeam {
        require(leader.userId == triggerUserId) {
            "팀장만 팀 정보를 수정할 수 있어요!"
        }

        require(this.status != MeetingTeamStatus.PUBLISHED) {
            "이미 공개된 팀 정보는 수정할 수 없어요!"
        }

        require(memberCount != null && members.size <= memberCount) {
            "현재 팀원수보다 적게 팀원수를 변경할 수 없어요!"
        }

        return copy(
            teamIntroduce = teamIntroduce ?: this.teamIntroduce,
            memberCount = memberCount,
            location = location ?: this.location,
        )
    }

    fun isPublished(): Boolean {
        return status == MeetingTeamStatus.PUBLISHED
    }

    private fun publish(): MeetingTeam {
        return copy(status = MeetingTeamStatus.PUBLISHED)
    }

    companion object {

        fun create(
            teamIntroduce: TeamIntroduce,
            memberCount: Int,
            leader: User,
            location: Location,
        ): MeetingTeam {
            val leaderMember =
                MeetingMember.create(userId = leader.id, role = MeetingMemberRole.LEADER)

            return MeetingTeam(
                teamIntroduce = teamIntroduce,
                memberCount = memberCount,
                members = listOf(leaderMember),
                location = location,
                status = MeetingTeamStatus.WAITING,
                gender = leader.gender,
            )
        }
    }

}
