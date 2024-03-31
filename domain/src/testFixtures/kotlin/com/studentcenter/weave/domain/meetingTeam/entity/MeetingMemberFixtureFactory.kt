package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.*

object MeetingMemberFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        userId: UUID = UuidCreator.create(),
        role: MeetingMemberRole = MeetingMemberRole.MEMBER,
    ): MeetingMember {
        return MeetingMember(
            id = id,
            userId = userId,
            role = role,
        )
    }

}
