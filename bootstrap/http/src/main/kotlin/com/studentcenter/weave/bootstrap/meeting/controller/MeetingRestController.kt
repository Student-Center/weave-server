package com.studentcenter.weave.bootstrap.meeting.controller

import com.studentcenter.weave.application.meeting.port.inbound.MeetingRequestUseCase
import com.studentcenter.weave.bootstrap.meeting.api.MeetingApi
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingRequestRequest
import org.springframework.web.bind.annotation.RestController

@RestController
class MeetingRestController(
    private val meetingRequestUseCase: MeetingRequestUseCase,
) : MeetingApi {

    override fun requestMeeting(request: MeetingRequestRequest) {
        MeetingRequestUseCase.Command(
            receivingMeetingTeamId = request.receivingMeetingTeamId,
        ).also {
            meetingRequestUseCase.invoke(it)
        }
    }

}
