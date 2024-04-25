package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.common.DomainEntity
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class MeetingTeamMemberSummary(
    override val id: UUID = UuidCreator.create(),
    val meetingTeamId: UUID,
    val teamMbti: Mbti,
    val youngestMemberBirthYear: BirthYear,
    val oldestMemberBirthYear: BirthYear,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) : DomainEntity {

    init {
        require(youngestMemberBirthYear.value >= oldestMemberBirthYear.value) {
            "가장 나이가 어린 멤버의 년생은 가장 나이가 많은 멤버의 년생보다 작거나 같아야 합니다."
        }
    }

    companion object {

        fun MeetingTeam.createSummary(
            getUsersByMeetingMembers: (List<MeetingMember>) -> List<User>
        ): MeetingTeamMemberSummary {
            val users: List<User> = getUsersByMeetingMembers(members)

            return MeetingTeamMemberSummary(
                meetingTeamId = this.id,
                teamMbti = getTeamMbti(users),
                youngestMemberBirthYear = getYoungestMemberBirthYear(users),
                oldestMemberBirthYear = getOldestMemberBirthYear(users)
            )
        }

        private fun getTeamMbti(members: List<User>): Mbti {
            return members
                .map { it.mbti }
                .toList()
                .let { Mbti.getDominantMbti(it) }
        }

        private fun getYoungestMemberBirthYear(members: List<User>): BirthYear {
            // 가장 어린 멤버는 태어난 해가 가장 느리다(max)
            return members
                .map { it.birthYear }
                .maxBy { it.value }
        }

        private fun getOldestMemberBirthYear(members: List<User>): BirthYear {
            // 가장 나이든 멤버는 태어난 해가 가장 빠르다(min)
            return members
                .map { it.birthYear }
                .minBy { it.value }
        }
    }

}
