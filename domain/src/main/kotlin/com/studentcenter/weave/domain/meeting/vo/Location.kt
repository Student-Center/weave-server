package com.studentcenter.weave.domain.meeting.vo

@JvmInline
value class Location(val value: String) {

    init {
        require(value.length in 1..20) {
            "위치는 1자 이상 20자 이하여야 합니다."
        }
    }

}
