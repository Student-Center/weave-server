package com.studentcenter.weave.bootstrap.meeting.controller

import com.studentcenter.weave.application.meeting.port.inbound.FindMyRequestMeetingByReceivingTeamIdUseCase
import com.studentcenter.weave.application.meeting.port.inbound.GetAllOtherTeamMemberInfoUseCase
import com.studentcenter.weave.application.meeting.port.inbound.GetMeetingAttendancesUseCase
import com.studentcenter.weave.application.meeting.port.inbound.MeetingAttendanceCreateUseCase
import com.studentcenter.weave.application.meeting.port.inbound.MeetingRequestUseCase
import com.studentcenter.weave.application.meeting.port.inbound.ScrollPendingMeetingUseCase
import com.studentcenter.weave.bootstrap.meeting.api.MeetingApi
import com.studentcenter.weave.bootstrap.meeting.dto.KakaoIdResponse
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingAttendancesResponse
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingRequestRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingResponse
import com.studentcenter.weave.bootstrap.meeting.dto.PendingMeetingScrollRequest
import com.studentcenter.weave.bootstrap.meeting.dto.PendingMeetingScrollResponse
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class MeetingRestController(
    private val meetingRequestUseCase: MeetingRequestUseCase,
    private val scrollPendingMeetingUseCase: ScrollPendingMeetingUseCase,
    private val getMeetingAttendancesUseCase: GetMeetingAttendancesUseCase,
    private val meetingAttendanceCreateUseCase: MeetingAttendanceCreateUseCase,
    private val findMyRequestMeetingByReceivingTeamIdUseCase: FindMyRequestMeetingByReceivingTeamIdUseCase,
    private val getAllOtherTeamMemberInfoUseCase: GetAllOtherTeamMemberInfoUseCase,
) : MeetingApi {

    override fun requestMeeting(request: MeetingRequestRequest) {
        MeetingRequestUseCase.Command(
            receivingMeetingTeamId = request.receivingMeetingTeamId,
        ).also {
            meetingRequestUseCase.invoke(it)
        }
    }

    override fun scrollPendingMeetings(
        request: PendingMeetingScrollRequest,
    ) : PendingMeetingScrollResponse {
        return scrollPendingMeetingUseCase.invoke(request.toCommand()).let {
            PendingMeetingScrollResponse(
                items = it.items.map(PendingMeetingScrollResponse.MeetingDto::from),
                next = it.next,
                total = it.total,
            )
        }

    }

    override fun getMeetingAttendances(meetingId: UUID): MeetingAttendancesResponse {
        return getMeetingAttendancesUseCase.invoke(meetingId).let {
            MeetingAttendancesResponse.from(meetingAttendances = it)
        }
    }

    override fun createAttendanceForAttend(meetingId: UUID) {
        meetingAttendanceCreateUseCase.invoke(
            meetingId = meetingId,
            attendance = true,
        )
    }

    override fun createAttendanceForPass(meetingId: UUID) {
        meetingAttendanceCreateUseCase.invoke(
            meetingId = meetingId,
            attendance = false,
        )
    }

    override fun findMyRequestMeetingByReceivingTeamId(receivingTeamId: UUID) : MeetingResponse {
        return findMyRequestMeetingByReceivingTeamIdUseCase.invoke(receivingTeamId).let {
            MeetingResponse.from(it)
        }
    }

    override fun getOtherTeamKakaoIds(meetingId: UUID): KakaoIdResponse {
        return getAllOtherTeamMemberInfoUseCase.invoke(meetingId).let {
            KakaoIdResponse.from(it)
        }
    }


}
