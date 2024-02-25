package com.studentcenter.weave.application.meetingTeam.vo

import com.studentcenter.weave.support.common.vo.Url
import java.util.*
import kotlin.time.Duration

data class MeetingTeamInvitation(
    val teamId: UUID,
    val invitationCode: UUID,
    val invitationLink: Url,
    val expirationDuration: Duration,
)
