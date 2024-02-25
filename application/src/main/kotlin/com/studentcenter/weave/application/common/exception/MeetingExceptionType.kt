package com.studentcenter.weave.application.common.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class MeetingExceptionType (override val code: String): CustomExceptionType {
    FINISHED_MEETING("MEETING-001"),
    MEETING_NOT_JOINED_USER("MEETING-002"),
    ALREADY_ATTENDANCE_CREATED("MEETING-003"),
}
