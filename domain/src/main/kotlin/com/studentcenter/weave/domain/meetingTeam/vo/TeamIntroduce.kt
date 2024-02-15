package com.studentcenter.weave.domain.meetingTeam.vo

@JvmInline
value class TeamIntroduce(val value: String) {

    init {
        require(value.length in 1..10) {
            "팀 한줄 소개는 1자 이상 10자 이하여야 합니다."
        }
    }

}
