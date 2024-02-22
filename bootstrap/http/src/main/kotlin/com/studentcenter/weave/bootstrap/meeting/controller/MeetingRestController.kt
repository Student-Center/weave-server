package com.studentcenter.weave.bootstrap.meeting.controller

import com.studentcenter.weave.application.meeting.port.inbound.MeetingRequestUseCase
import com.studentcenter.weave.application.meeting.port.inbound.ScrollPendingMeetingUseCase
import com.studentcenter.weave.bootstrap.meeting.api.MeetingApi
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingRequestRequest
import com.studentcenter.weave.bootstrap.meeting.dto.PendingMeetingScrollRequest
import com.studentcenter.weave.bootstrap.meeting.dto.PendingMeetingScrollResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class MeetingRestController(
    private val meetingRequestUseCase: MeetingRequestUseCase,
    private val scrollPendingMeetingUseCase: ScrollPendingMeetingUseCase,
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
        val result = scrollPendingMeetingUseCase.invoke(request.toCommand())
        return PendingMeetingScrollResponse(
            items = result.items.map { PendingMeetingScrollResponse.MeetingDto.from(it) },
            next = result.next,
            total = result.total,
        )
    }


}
