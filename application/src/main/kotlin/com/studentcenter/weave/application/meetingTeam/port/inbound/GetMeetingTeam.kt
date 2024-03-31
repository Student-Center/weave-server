package com.studentcenter.weave.application.meetingTeam.port.inbound

import com.studentcenter.weave.application.meetingTeam.vo.MeetingMemberDetail
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeam
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import com.studentcenter.weave.domain.meetingTeam.enums.MeetingTeamStatus
import java.util.*

interface GetMeetingTeam {

    fun getById(meetingTeamId: UUID): MeetingTeam

    fun getByIdAndStatus(
        meetingTeamId: UUID,
        status: MeetingTeamStatus,
    ): MeetingTeam

    fun getByMemberUserId(memberUserId: UUID): MeetingTeam

    fun findByMemberUserId(userId: UUID): MeetingTeam?

    fun getMeetingTeamMemberSummaryByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary

    fun getMemberDetail(
        meetingTeamId: UUID,
        memberId: UUID,
    ): MeetingMemberDetail

}
