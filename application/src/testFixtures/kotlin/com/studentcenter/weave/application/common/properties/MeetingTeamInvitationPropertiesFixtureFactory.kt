package com.studentcenter.weave.application.common.properties

class MeetingTeamInvitationPropertiesFixtureFactory {

    companion object {

        fun create(
            expireSeconds: Long = 86400
        ): MeetingTeamInvitationProperties {
            return MeetingTeamInvitationProperties(
                expireSeconds = expireSeconds
            )
        }

    }

}
