package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.domain.meeting.enums.Location

data class MeetingTeamLocationResponse(
    val locations: List<MeetingTeamLocationDto>
) {

    data class MeetingTeamLocationDto(
        val name: String,
        val displayName: String,
        val isCapitalArea: Boolean
    )

    companion object {
        private var instance: MeetingTeamLocationResponse? = null

        fun getInstance(): MeetingTeamLocationResponse {

            return instance ?: MeetingTeamLocationResponse(
                Location.entries.map {
                    MeetingTeamLocationDto(it.name, it.value, it.isCapitalArea)
                }.also { instance }
            )

        }

    }

}
