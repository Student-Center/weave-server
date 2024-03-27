package com.studentcenter.weave.application.common.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class MeetingTeamExceptionType (override val code: String): CustomExceptionType {
    IS_NOT_TEAM_MEMBER("MEETING-TEAM-001"),
    LEADER_CANNOT_LEAVE_TEAM("MEETING-TEAM-002"),
    INVITATION_CODE_NOT_FOUND("MEETING-TEAM-003"),
    ALREADY_JOINED_MEMBER("MEETING-TEAM-004"),
}
