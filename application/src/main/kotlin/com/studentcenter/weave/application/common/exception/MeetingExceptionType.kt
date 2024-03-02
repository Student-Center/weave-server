package com.studentcenter.weave.application.common.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class MeetingExceptionType (override val code: String): CustomExceptionType {
    FINISHED_MEETING("MEETING-001"),
    MEETING_NOT_JOINED_USER("MEETING-002"),
    ALREADY_ATTENDANCE_CREATED("MEETING-003"),
    NOT_FOUND_MY_MEETING_TEAM("MEETING-004"),
    CAN_NOT_PUBLISHED_TEAM("MEETING-005"),
    CAN_NOT_MEETING_REQUEST_NOT_UNIV_VERIFIED_USER("MEETING-006"),
    CAN_NOT_MEETING_REQUEST_SAME_GENDER("MEETING-007"),
    CAN_NOT_MEETING_REQUEST_NOT_SAME_MEMBERS("MEETING-008"),
    ALREADY_REQUEST_MEETING("MEETING-009"),
}
