package com.studentcenter.weave.bootstrap.meetingTeam.api

import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamCreateInvitationResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamCreateRequest
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamEditRequest
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetByInvitationCodeResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetDetailResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetListRequest
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetListResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetLocationsResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetMyRequest
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamGetMyResponse
import com.studentcenter.weave.bootstrap.meetingTeam.dto.MeetingTeamMemberDetailResponse
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

    @Operation(summary = "Get meeting locations")
    @GetMapping("/locations")
    @ResponseStatus(HttpStatus.OK)
    fun getMeetingTeamLocations(): MeetingTeamGetLocationsResponse

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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun editMeetingTeam(
        @PathVariable
        id: UUID,
        @RequestBody
        request: MeetingTeamEditRequest,
    )

    @Secured
    @Operation(summary = "Get meeting team detail by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getMeetingTeamDetail(
        @PathVariable
        id: UUID,
    ): MeetingTeamGetDetailResponse

    @Secured
    @Operation(summary = "Get meeting team list")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getMeetingTeams(
        request: MeetingTeamGetListRequest
    ): MeetingTeamGetListResponse

    @Secured
    @Operation(summary = "Leave meeting team")
    @DeleteMapping("/{id}/members/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun leaveMeetingTeam(
        @PathVariable
        id: UUID
    )

    @Secured
    @Operation(summary = "Create meeting team invitation Link")
    @PostMapping("/{meetingTeamId}/invitation")
    @ResponseStatus(HttpStatus.OK)
    fun createMeetingTeamInvitation(
        @PathVariable
        meetingTeamId: UUID
    ): MeetingTeamCreateInvitationResponse

    @Secured
    @Operation(summary = "Get meeting Team by invitation code")
    @GetMapping("/invitation/{invitationCode}")
    @ResponseStatus(HttpStatus.OK)
    fun getMeetingTeamByInvitationCode(
        @PathVariable
        invitationCode: UUID
    ): MeetingTeamGetByInvitationCodeResponse

    @Secured
    @Operation(summary = "Enter meeting team")
    @PostMapping("/invitation/{invitationCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun joinMeetingTeam(
        @PathVariable
        invitationCode: UUID
    )

    @Secured
    @Operation(summary = "Get meeting team member detail")
    @GetMapping("/{teamId}/members/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    fun getMeetingTeamMemberDetail(
        @PathVariable teamId: UUID,
        @PathVariable memberId: UUID,
    ): MeetingTeamMemberDetailResponse

}
