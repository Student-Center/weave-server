package com.studentcenter.weave.bootstrap.meeting.api

import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingRequestRequest
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "Meeting", description = "Meeting API")
@RequestMapping("/api/meetings", produces = ["application/json;charset=utf-8"])
interface MeetingApi {

    @Secured
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun requestMeeting(
        @RequestBody
        request: MeetingRequestRequest
    )

}
