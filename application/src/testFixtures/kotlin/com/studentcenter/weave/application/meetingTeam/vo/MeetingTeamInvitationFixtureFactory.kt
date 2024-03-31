package com.studentcenter.weave.application.meetingTeam.vo

import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Url
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

object MeetingTeamInvitationFixtureFactory {

    fun create(
        teamId: UUID = UuidCreator.create(),
        invitationCode: UUID = UuidCreator.create(),
        invitationLink: Url = Url("https://weave.com/invitation/$invitationCode"),
        expirationDuration: Duration = 1.hours,
    ): MeetingTeamInvitation {
        return MeetingTeamInvitation(
            teamId = teamId,
            invitationCode = invitationCode,
            invitationLink = invitationLink,
            expirationDuration = expirationDuration
        )
    }


}
