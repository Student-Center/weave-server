package com.studentcenter.weave.application.common.properties

import kotlin.time.Duration.Companion.hours

class MeetingTeamInvitationPropertiesFixtureFactory {

    companion object {
        private const val INVITATION_URL_PREFIX = "https://api.dev.team-weave.me?code="
        private val DEFAULT_EXPIRE_DURATION = 1.hours

        fun create(): MeetingTeamInvitationProperties {
            return MeetingTeamInvitationProperties(
                expireSeconds = DEFAULT_EXPIRE_DURATION,
                urlPrefix = INVITATION_URL_PREFIX,
            )
        }

    }

}
