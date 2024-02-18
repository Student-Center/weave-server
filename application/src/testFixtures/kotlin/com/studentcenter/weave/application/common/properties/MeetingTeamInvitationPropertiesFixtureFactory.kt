package com.studentcenter.weave.application.common.properties

class MeetingTeamInvitationPropertiesFixtureFactory {

    companion object {
        private const val EXPIRE_DURATION = 3600L

        fun create(): MeetingTeamInvitationProperties {
            return MeetingTeamInvitationProperties(
                expireDuration = EXPIRE_DURATION
            )
        }

    }

}
