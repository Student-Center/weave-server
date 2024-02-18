package com.studentcenter.weave.application.meetingTeam.util

import java.util.*


interface MeetingTeamInvitationService {

    fun create(teamId: UUID): UUID

}
