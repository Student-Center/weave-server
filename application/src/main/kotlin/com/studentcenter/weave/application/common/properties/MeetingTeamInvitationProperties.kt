package com.studentcenter.weave.application.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import kotlin.time.Duration

@ConfigurationProperties(value = "meeting.team.invitation")
data class MeetingTeamInvitationProperties(
    val urlPrefix: String,
    val expireSeconds: Duration,
)
