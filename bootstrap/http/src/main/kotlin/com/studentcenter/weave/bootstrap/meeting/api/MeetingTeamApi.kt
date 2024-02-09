package com.studentcenter.weave.bootstrap.meeting.api

import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamCreateRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamGetMyResponse
import com.studentcenter.weave.support.common.dto.ScrollRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "Meeting Team", description = "Meeting Team API")
@RequestMapping("/api/meeting-teams")
interface MeetingTeamApi {

    @Secured
    @Operation(summary = "Create new meeting team")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMeetingTeam(
        @RequestBody
        request: MeetingTeamCreateRequest
    )

    @Secured
    @Operation(summary = "Get my meeting teams")
    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    fun getMyMeetingTeams(
        scrollRequest: ScrollRequest
    ): MeetingTeamGetMyResponse

}
