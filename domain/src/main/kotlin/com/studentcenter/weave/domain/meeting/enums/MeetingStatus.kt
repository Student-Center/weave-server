package com.studentcenter.weave.domain.meeting.enums

enum class MeetingStatus {
    PENDING,
    // FIXME: 현재 사용하지 않는 상태(MVP 스펙 X)
    // PREPARING,
    // SCHEDULED,
    // INSUFFICIENT,
    COMPLETED,
    CANCELED;
}
