package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.domain.meeting.enums.Location

data class MeetingTeamLocationResponse(
    val locations: List<MeetingTeamLocationDto>
) {

    data class MeetingTeamLocationDto(
        val item: String,
        val value: String
    )

    companion object {

        fun of(): MeetingTeamLocationResponse {
            return MeetingTeamLocationResponse(Location.entries.map {
                MeetingTeamLocationDto(it.name, it.value)
            })
        }

    }

}
