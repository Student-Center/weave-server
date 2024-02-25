package com.studentcenter.weave.application.meeting.service.application

import com.studentcenter.weave.application.common.exception.MeetingExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.meeting.port.inbound.MeetingAttendanceCreateUseCase
import com.studentcenter.weave.application.meeting.service.domain.MeetingAttendanceDomainService
import com.studentcenter.weave.application.meeting.service.domain.MeetingDomainService
import com.studentcenter.weave.application.meetingTeam.port.inbound.MeetingTeamMemberQueryUseCase
import com.studentcenter.weave.domain.meeting.entity.Meeting
import com.studentcenter.weave.domain.meeting.entity.MeetingAttendance
import com.studentcenter.weave.domain.meetingTeam.entity.MeetingMember
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class MeetingAttendanceCreateApplicationService(
    private val meetingDomainService: MeetingDomainService,
    private val meetingAttendanceDomainService: MeetingAttendanceDomainService,
    private val meetingTeamMemberQueryUseCase: MeetingTeamMemberQueryUseCase
) : MeetingAttendanceCreateUseCase {

    @Transactional
    override fun invoke(meetingId: UUID, attendance: Boolean) {
        val meeting = getByIdAndValidate(meetingId)
        val meetingMembers = meetingTeamMemberQueryUseCase.findAllByTeamIds(
            teamIds = listOf(meeting.requestingTeamId, meeting.receivingTeamId),
        )
        val teamMember = meetingMembers.firstOrNull {
            it.userId == getCurrentUserAuthentication().userId
        } ?: throw CustomException(
            MeetingExceptionType.MEETING_NOT_JOINED_USER,
            "미팅에 참여하지 않는 유저입니다",
        )

        validateAlreadyCreatedAttendance(
            meetingId = meeting.id,
            meetingMemberId = teamMember.id
        )

        MeetingAttendance.create(
            meetingId = meeting.id,
            meetingMemberId = teamMember.id,
            isAttend = attendance,
        ).also {
            meetingAttendanceDomainService.save(it)
        }

        meetingUpdateIfNeeded(
            meeting = meeting,
            meetingMembers = meetingMembers,
            isAttendance = attendance,
        )

    }

    private fun meetingUpdateIfNeeded(
        meeting: Meeting,
        meetingMembers: List<MeetingMember>,
        isAttendance: Boolean,
    ) {
        if (isAttendance.not()) {
            meetingDomainService.save(meeting.cancel())
            return
        }

        if (meetingAttendanceDomainService.countByMeetingId(meeting.id) == meetingMembers.size) {
            meetingDomainService.save(meeting.complete())
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
        )) {
            throw CustomException(
                MeetingExceptionType.ALREADY_ATTENDANCE_CREATED,
                "이미 미팅 참여 의사를 결정했습니다.",
            )
        }
    }

}
