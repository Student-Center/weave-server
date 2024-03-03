package com.studentcenter.weave.bootstrap.meeting.api

import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import com.studentcenter.weave.bootstrap.meeting.dto.KakaoIdResponse
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingAttendancesResponse
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingRequestRequest
import com.studentcenter.weave.bootstrap.meeting.dto.MeetingResponse
import com.studentcenter.weave.bootstrap.meeting.dto.PendingMeetingScrollRequest
import com.studentcenter.weave.bootstrap.meeting.dto.PendingMeetingScrollResponse
import com.studentcenter.weave.domain.user.vo.KakaoId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@Tag(name = "Meeting", description = "Meeting API")
@RequestMapping("/api/meetings", produces = ["application/json;charset=utf-8"])
interface MeetingApi {

    @Secured
    @Operation(summary = "Request meeting")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun requestMeeting(
        @RequestBody
        request: MeetingRequestRequest
    )

    @Secured
    @Operation(summary = "Scroll pending meetings for requested or received")
    @GetMapping("/status/pending")
    @ResponseStatus(HttpStatus.OK)
    fun scrollPendingMeetings(
        request: PendingMeetingScrollRequest,
    ) : PendingMeetingScrollResponse

    @Secured
    @Operation(summary = "Get all meeting attendances by meeting id")
    @GetMapping("/{id}/attendance")
    @ResponseStatus(HttpStatus.OK)
    fun getMeetingAttendances(
        @PathVariable("id") meetingId: UUID,
    ) : MeetingAttendancesResponse

    @Secured
    @Operation(summary = "Create meeting attendance for attend")
    @PostMapping("{id}/attendance:attend")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAttendanceForAttend(
        @PathVariable("id") meetingId: UUID,
    )

    @Secured
    @Operation(summary = "Create meeting attendance for pass")
    @PostMapping("{id}/attendance:pass")
    @ResponseStatus(HttpStatus.CREATED)
    fun createAttendanceForPass(
        @PathVariable("id") meetingId: UUID,
    )

    @Secured
    @Operation(summary = "Find request meeting by team id")
    @GetMapping("requesting-team/my")
    @ResponseStatus(HttpStatus.OK)
    fun findMyRequestMeetingByReceivingTeamId(
        @Parameter(description = "receiving team id", required = true, `in` = ParameterIn.QUERY)
        receivingTeamId: UUID,
    ) : MeetingResponse

    @Secured
    @Operation(summary = "Get other team member's kakao id by meeting id")
    @GetMapping("{id}/other-team/kakao-id")
    @ResponseStatus(HttpStatus.OK)
    fun getOtherTeamKakaoIds(@PathVariable("id") meetingId: UUID) : KakaoIdResponse

}
