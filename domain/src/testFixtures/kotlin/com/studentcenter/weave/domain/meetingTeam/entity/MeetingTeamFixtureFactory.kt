package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.meetingTeam.enums.Location
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import com.studentcenter.weave.domain.meetingTeam.vo.TeamIntroduce
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.*

object MeetingTeamFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        memberCount: Int = 4,
        teamIntroduce: TeamIntroduce = TeamIntroduce("팀 소개"),
        location: Location = Location.SUWON,
        status: MeetingTeamStatus = MeetingTeamStatus.WAITING,
        gender: Gender = Gender.MAN,
        leader: User = UserFixtureFactory.create(),
        members: List<User> = emptyList(),
    ): MeetingTeam {
        val leaderMeetingMember = MeetingMember.create(
            userId = leader.id,
            role = MeetingMemberRole.LEADER,
        )

        val memberMeetingMembers = members.map {
            MeetingMember.create(
                userId = it.id,
                role = MeetingMemberRole.MEMBER,
            )
        }

        return MeetingTeam(
            id = id,
            memberCount = memberCount,
            teamIntroduce = teamIntroduce,
            location = location,
            status = status,
            gender = gender,
            members = listOf(leaderMeetingMember) + memberMeetingMembers,
        )
    }
}
