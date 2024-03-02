package com.studentcenter.weave.application.meetingTeam.vo

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamFixtureFactory
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.domain.user.entity.UniversityFixtureFactory
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.common.uuid.UuidCreator
import kotlin.random.Random
import kotlin.random.nextUInt

object MeetingTeamInfoCreator {
    fun create(
        users: List<User> = emptyList(),
        memberCount: Int = 2,
        gender: Gender = Gender.MAN,
    ): MeetingTeamInfo {
        val g = users.distinctBy { it.gender }
        require(g.size <= 1) { "성별이 다른 멤버로 팀을 만들 수 없어요" }
        require(users.isEmpty() || users[0].gender == gender ) { "멤버와 성별이 다를 수 없어요" }
        val team = MeetingTeamFixtureFactory.create(
            memberCount = memberCount,
            status = MeetingTeamStatus.PUBLISHED,
            gender = gender
        )
        val members = MutableList(users.size) { i ->
            MeetingTeamInfo.MemberInfo(
                id = UuidCreator.create(),
                user = users[i],
                university = UniversityFixtureFactory.create(
                    id = users[i].universityId,
                    name = UniversityName(value = "${Random.nextUInt()}대학교"),
                ),
                role = if (i == 0) MeetingMemberRole.LEADER else MeetingMemberRole.MEMBER
            )
        }

        if (users.size < memberCount) {
            repeat(memberCount - users.size) { i ->
                val user = UserFixtureFactory.create(gender = gender)
                members.add(
                    MeetingTeamInfo.MemberInfo(
                        id = UuidCreator.create(),
                        user = user,
                        university = UniversityFixtureFactory.create(
                            id = user.universityId,
                            name = UniversityName(value = "${Random.nextUInt()}대학교"),
                        ),
                        role = if (users.isEmpty() && i == 0) MeetingMemberRole.LEADER else MeetingMemberRole.MEMBER
                    )
                )
            }
        }

        return MeetingTeamInfo(
            team = team,
            memberInfos = members
        )
    }
}
