package com.studentcenter.weave.application.meeting.port.outbound

import com.studentcenter.weave.domain.meeting.entity.MeetingTeam

interface MeetingTeamRepository {

    fun save(meetingTeam: MeetingTeam)

}
