package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.CreateMeetingAttendance
import com.studentcenter.weave.application.meeting.port.outbound.MeetingEventPublisher
import com.studentcenter.weave.application.meeting.service.domain.MeetingAttendanceDomainService
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import com.studentcenter.weave.domain.meeting.event.MeetingCompletedEvent.Companion.createCompletedEvent
import com.studentcenter.weave.domain.meeting.exception.MeetingException
import com.studentcenter.weave.support.lock.distributedLock
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class CreateMeetingAttendanceService(
    private val meetingDomainService: MeetingDomainService,
    private val meetingAttendanceDomainService: MeetingAttendanceDomainService,
    private val getMeetingTeam: GetMeetingTeam,
    private val meetingEventPublisher: MeetingEventPublisher,
) : CreateMeetingAttendance {

    override fun invoke(
        meetingId: UUID,
        attendance: Boolean,
    ) = distributedLock("${this.javaClass.simpleName}:$meetingId") {
        val meeting = getByIdAndValidate(meetingId)

        val requestingTeam = getMeetingTeam.getById(meeting.requestingTeamId)
        val receivingTeam = getMeetingTeam.getById(meeting.receivingTeamId)

        val teamMembers = requestingTeam.members + receivingTeam.members

        val teamMemberMe = teamMembers
            .firstOrNull { it.userId == getCurrentUserAuthentication().userId }
            ?: throw MeetingException.NotJoinedUser()

        validateAlreadyCreatedAttendance(
            meetingId = meeting.id,
            meetingMemberId = teamMemberMe.id
        )

        MeetingAttendance.create(
            meetingId = meeting.id,
            meetingMemberId = teamMemberMe.id,
            isAttend = attendance,
        ).also {
            meetingAttendanceDomainService.save(it)
        }

        updateMeetingIfLastAttendanceOrCancel(
            meeting = meeting,
            memberCount = teamMembers.size,
            isAttendance = attendance,
        )

    }

    private fun updateMeetingIfLastAttendanceOrCancel(
        meeting: Meeting,
        memberCount: Int,
        isAttendance: Boolean,
    ) {
        if (isAttendance.not()) {
            updateMeetingStateToCancel(meeting)
            return
        }

        val countAttend = meetingAttendanceDomainService.countByMeetingIdAndIsAttend(meeting.id)

        if (countAttend == memberCount) {
            updateMeetingStateToComplete(meeting)
        }
    }

    private fun updateMeetingStateToCancel(meeting: Meeting) {
        meetingDomainService.save(meeting.cancel())
    }

    private fun updateMeetingStateToComplete(meeting: Meeting) {
        meeting
            .complete()
            .also {
                meetingDomainService.save(it)
                meetingEventPublisher.publish(it.createCompletedEvent())
            }
    }

    private fun getByIdAndValidate(meetingId: UUID): Meeting {
        val meeting = meetingDomainService.getById(meetingId)
        if (meeting.isFinished() || meeting.isEndPending(LocalDateTime.now())) {
            throw MeetingException.AlreadyFinished()
        }
        return meeting
    }

    private fun validateAlreadyCreatedAttendance(
        meetingId: UUID,
        meetingMemberId: UUID,
    ) {
        if (meetingAttendanceDomainService.existsByMeetingIdAndMeetingMemberId(
                meetingId = meetingId,
                meetingMemberId = meetingMemberId,
            )
        ) {
            throw MeetingException.AlreadyAttended()
        }
    }

}
