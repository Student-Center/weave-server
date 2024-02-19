package com.studentcenter.weave.application.common.properties

import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MeetingTeamInvitationPropertiesFixtureFactory {

    companion object {
        private const val INVITATION_URL_PREFIX = "https://api.dev.team-weave.me?code="
        private val DEFAULT_EXPIRE_SECONDS = 3600L.toDuration(DurationUnit.SECONDS)

        fun create(): MeetingTeamInvitationProperties {
            return MeetingTeamInvitationProperties(
                expireSeconds = DEFAULT_EXPIRE_SECONDS,
                urlPrefix = INVITATION_URL_PREFIX,
            )
        }

    }

}
