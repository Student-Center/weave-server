package com.studentcenter.weave.domain.meetingTeam.entity

import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import java.util.*

data class MeetingMember(
    val id: UUID,
    val meetingTeamId: UUID,
    val userId: UUID,
    val role: MeetingMemberRole,
) {

    companion object {

        fun create(
            meetingTeamId: UUID,
            userId: UUID,
            role: MeetingMemberRole,
        ): MeetingMember {
            return MeetingMember(
                id = UUID.randomUUID(),
                meetingTeamId = meetingTeamId,
                userId = userId,
                role = role,
            )
        }
    }

}
