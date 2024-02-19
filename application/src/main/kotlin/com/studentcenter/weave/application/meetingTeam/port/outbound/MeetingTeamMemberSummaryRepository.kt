package com.studentcenter.weave.application.meetingTeam.port.outbound

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import java.util.*

interface MeetingTeamMemberSummaryRepository {

    fun save(meetingTeamMemberSummary: MeetingTeamMemberSummary)

    fun deleteById(id: UUID)

    fun getById(id: UUID): MeetingTeamMemberSummary

    fun getByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary

}
