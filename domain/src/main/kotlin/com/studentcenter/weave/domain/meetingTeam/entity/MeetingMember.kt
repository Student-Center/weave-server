package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.common.DomainEntity
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.*

data class MeetingMember(
    override val id: UUID,
    val userId: UUID,
    val role: MeetingMemberRole,
) : DomainEntity {

    companion object {

        fun create(
            userId: UUID,
            role: MeetingMemberRole,
        ): MeetingMember {
            return MeetingMember(
                id = UuidCreator.create(),
                userId = userId,
                role = role,
            )
        }
    }

}
