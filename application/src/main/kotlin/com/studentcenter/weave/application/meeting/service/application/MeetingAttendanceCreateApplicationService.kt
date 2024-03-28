package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.exception.MeetingExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.MeetingAttendanceCreateUseCase
import com.studentcenter.weave.application.meeting.port.outbound.MeetingEventPort
import com.studentcenter.weave.application.meeting.service.domain.MeetingAttendanceDomainService
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meeting.vo.MeetingMatchingEvent
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeamMembers
import com.studentcenter.weave.application.meetingTeam.port.inbound.GetMeetingTeam
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.lock.distributedLock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class MeetingAttendanceCreateApplicationService(
    private val meetingDomainService: MeetingDomainService,
    private val meetingAttendanceDomainService: MeetingAttendanceDomainService,
    private val getMeetingTeamMembers: GetMeetingTeamMembers,
    private val getMeetingTeam: GetMeetingTeam,
    private val meetingEventPort: MeetingEventPort,
) : MeetingAttendanceCreateUseCase {

    override fun invoke(
        meetingId: UUID,
        attendance: Boolean,
    ): Unit = distributedLock("${this.javaClass.simpleName}:$meetingId") {
        val meeting = getByIdAndValidate(meetingId)
        val teamMembers = getMeetingTeamMembers.findAllByTeamIds(
            teamIds = listOf(meeting.requestingTeamId, meeting.receivingTeamId),
        )
        val teamMemberMe = teamMembers.firstOrNull {
            it.userId == getCurrentUserAuthentication().userId
        } ?: throw CustomException(
            MeetingExceptionType.MEETING_NOT_JOINED_USER,
            "미팅에 참여하지 않는 유저입니다",
        )

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

        updateMeetingIfNeeded(
            meeting = meeting,
            memberCount = teamMembers.size,
            isAttendance = attendance,
        )

    }

    private fun updateMeetingIfNeeded(
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
            updateMeetingStateToComplete(
                meeting = meeting,
                memberCount = memberCount,
            )
        }
    }

    private fun updateMeetingStateToCancel(meeting: Meeting) {
        meetingDomainService.save(meeting.cancel())
    }

    private fun updateMeetingStateToComplete(
        meeting: Meeting,
        memberCount: Int,
    ) {
        meetingDomainService.save(meeting.complete())

        val matchedMeetingCount = meetingDomainService.countByStatusIsCompleted()

        CoroutineScope(Dispatchers.IO).launch {
            val requestingMeetingTeamMemberSummary =
                getMeetingTeam.getMeetingTeamMemberSummaryByMeetingTeamId(meeting.requestingTeamId)
            val receivingMeetingTeamMemberSummary =
                getMeetingTeam.getMeetingTeamMemberSummaryByMeetingTeamId(meeting.receivingTeamId)

            meetingEventPort.sendMeetingIsMatchedMessage(
                MeetingMatchingEvent(
                    meeting = meeting,
                    memberCount = memberCount,
                    matchedMeetingCount = matchedMeetingCount,
                    requestingMeetingTeamMbti = requestingMeetingTeamMemberSummary.teamMbti,
                    receivingMeetingTeamMbti = receivingMeetingTeamMemberSummary.teamMbti,
                )
            )
        }
    }

    private fun getByIdAndValidate(meetingId: UUID): Meeting {
        val meeting = meetingDomainService.getById(meetingId)
        if (meeting.isFinished() || meeting.isEndPending(LocalDateTime.now())) {
            throw CustomException(
                MeetingExceptionType.FINISHED_MEETING,
                "이미 완료(혹은 종료)된 미팅입니다.",
            )
        }
        return meeting
    }

    private fun validateAlreadyCreatedAttendance(meetingId: UUID, meetingMemberId: UUID) {
        if (meetingAttendanceDomainService.existsByMeetingIdAndMeetingMemberId(
                meetingId = meetingId,
                meetingMemberId = meetingMemberId,
            )
        ) {
            throw CustomException(
                MeetingExceptionType.ALREADY_ATTENDANCE_CREATED,
                "이미 미팅 참여 의사를 결정했습니다.",
            )
        }
    }

}
