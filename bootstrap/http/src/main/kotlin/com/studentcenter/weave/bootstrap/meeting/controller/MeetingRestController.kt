package com.studentcenter.weave.bootstrap.meeting.controller

import com.studentcenter.weave.application.meeting.port.inbound.CreateMeetingAttendance
import com.studentcenter.weave.application.meeting.port.inbound.FindMyRequestMeetingByReceivingTeamId
import com.studentcenter.weave.application.meeting.port.inbound.GetAllOtherTeamMemberInfo
import com.studentcenter.weave.application.meeting.port.inbound.GetMeetingAttendances
import com.studentcenter.weave.application.meeting.port.inbound.RequestMeeting
import com.studentcenter.weave.application.meeting.port.inbound.ScrollPendingMeeting
import com.studentcenter.weave.application.meeting.port.inbound.ScrollPreparedMeeting
import com.studentcenter.weave.bootstrap.meeting.api.MeetingApi
import com.studentcenter.weave.bootstrap.meeting.dto.KakaoIdResponse
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingAttendancesResponse
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingRequestRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingResponse
import com.studentcenter.weave.bootstrap.meeting.dto.PendingMeetingScrollRequest
import com.studentcenter.weave.bootstrap.meeting.dto.PendingMeetingScrollResponse
import com.studentcenter.weave.bootstrap.meeting.dto.PreparedMeetingScrollRequest
import com.studentcenter.weave.bootstrap.meeting.dto.PreparedMeetingScrollResponse
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class MeetingRestController(
    private val meetingRequest: RequestMeeting,
    private val scrollPendingMeeting: ScrollPendingMeeting,
    private val scrollPreparedMeeting: ScrollPreparedMeeting,
    private val getMeetingAttendances: GetMeetingAttendances,
    private val createMeetingAttendance: CreateMeetingAttendance,
    private val findMyRequestMeetingByReceivingTeamId: FindMyRequestMeetingByReceivingTeamId,
    private val getAllOtherTeamMemberInfo: GetAllOtherTeamMemberInfo,
) : MeetingApi {

    override fun requestMeeting(request: MeetingRequestRequest) {
        RequestMeeting.Command(
            receivingMeetingTeamId = request.receivingMeetingTeamId,
        ).also {
            meetingRequest.invoke(it)
        }
    }

    override fun scrollPendingMeetings(
        request: PendingMeetingScrollRequest,
    ): PendingMeetingScrollResponse {
        return scrollPendingMeeting.invoke(request.toCommand()).let {
            PendingMeetingScrollResponse(
                items = it.items.map(PendingMeetingScrollResponse.MeetingDto::from),
                next = it.next,
                total = it.total,
            )
        }

    }

    override fun getMeetingAttendances(meetingId: UUID): MeetingAttendancesResponse {
        return getMeetingAttendances.invoke(meetingId).let {
            MeetingAttendancesResponse.from(meetingAttendances = it)
        }
    }

    override fun createAttendanceForAttend(meetingId: UUID) {
        createMeetingAttendance.invoke(
            meetingId = meetingId,
            attendance = true,
        )
    }

    override fun createAttendanceForPass(meetingId: UUID) {
        createMeetingAttendance.invoke(
            meetingId = meetingId,
            attendance = false,
        )
    }

    override fun findMyRequestMeetingByReceivingTeamId(receivingTeamId: UUID): MeetingResponse {
        return findMyRequestMeetingByReceivingTeamId.invoke(receivingTeamId).let {
            MeetingResponse.from(it)
        }
    }

    override fun scrollPreparedMeetings(request: PreparedMeetingScrollRequest): PreparedMeetingScrollResponse {
        return scrollPreparedMeeting.invoke(request.toCommand()).let {
            PreparedMeetingScrollResponse(
                items = it.items.map(PreparedMeetingScrollResponse.MeetingDto::from),
                next = it.next,
                total = it.total
            )
        }
    }

    override fun getOtherTeamKakaoIds(meetingId: UUID): KakaoIdResponse {
        return getAllOtherTeamMemberInfo.invoke(meetingId).let {
            KakaoIdResponse.from(it)
        }
    }


}
