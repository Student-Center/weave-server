package com.studentcenter.weave.bootstrap.meeting.api

import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamCreateRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamEditRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamGetMyRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingTeamGetMyResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

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
        request: MeetingTeamGetMyRequest
    ): MeetingTeamGetMyResponse

    @Secured
    @Operation(summary = "Delete meeting team by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMeetingTeam(
        @PathVariable
        id: UUID
    )

    @Secured
    @Operation(summary = "Edit meeting team by id")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun editMeetingTeam(
        @PathVariable
        id: UUID,
        @RequestBody
        request: MeetingTeamEditRequest,
    )

}
