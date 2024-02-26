package com.studentcenter.weave.application.common.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "meeting.team.invitation")
data class MeetingTeamInvitationProperties(
    val urlPrefix: String,
    val expireSeconds: Long,
)
