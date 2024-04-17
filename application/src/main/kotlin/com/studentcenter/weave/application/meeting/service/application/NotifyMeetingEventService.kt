package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.meeting.port.inbound.NotifyMeetingEvent
import com.studentcenter.weave.application.meeting.port.outbound.MeetingEventMessagePort
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meeting.vo.MeetingMatchingEvent
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.event.MeetingCompletedEvent
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotifyMeetingEventService(
    private val meetingDomainService: MeetingDomainService,
    private val meetingEventMessagePort: MeetingEventMessagePort,
    private val getMeetingTeam: GetMeetingTeam,
) : NotifyMeetingEvent {

    @Transactional
    override fun notifyMeetingCompleted(meetingCompletedEvent: MeetingCompletedEvent) {
        val meeting: Meeting = meetingCompletedEvent.entity
        val matchedMeetingCount: Int = meetingDomainService.countByStatusIsCompleted()

        val requestingMeetingTeamMemberSummary: MeetingTeamMemberSummary =
            getMeetingTeam.getMeetingTeamMemberSummaryByMeetingTeamId(meeting.requestingTeamId)
        val receivingMeetingTeamMemberSummary: MeetingTeamMemberSummary =
            getMeetingTeam.getMeetingTeamMemberSummaryByMeetingTeamId(meeting.receivingTeamId)

        MeetingMatchingEvent(
            meeting = meeting,
            memberCount = getMeetingMemberCount(meeting),
            matchedMeetingCount = matchedMeetingCount,
            requestingMeetingTeamMbti = requestingMeetingTeamMemberSummary.teamMbti,
            receivingMeetingTeamMbti = receivingMeetingTeamMemberSummary.teamMbti,
        ).also {
            meetingEventMessagePort.sendMeetingIsMatchedMessage(it)
        }
    }

    private fun getMeetingMemberCount(meeting: Meeting): Int {
        val requestingTeam = getMeetingTeam.getById(meeting.requestingTeamId)
        val receivingTeam = getMeetingTeam.getById(meeting.receivingTeamId)

        return requestingTeam.memberCount + receivingTeam.memberCount
    }


}
