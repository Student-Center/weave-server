package com.studentcenter.weave.application.common.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class MeetingExceptionType(override val code: String) : CustomExceptionType {
    LEADER_NOT_FOUND("MEETING-001"),
}
