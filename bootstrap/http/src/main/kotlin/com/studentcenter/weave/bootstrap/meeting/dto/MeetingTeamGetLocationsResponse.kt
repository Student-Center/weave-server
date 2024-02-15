package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.domain.meeting.enums.Location

data class MeetingTeamGetLocationsResponse(
    val locations: List<MeetingTeamLocationDto>
) {

    data class MeetingTeamLocationDto(
        val name: String,
        val displayName: String,
        val isCapitalArea: Boolean,
    )

    companion object {
        private val instance: MeetingTeamGetLocationsResponse by lazy {
            MeetingTeamGetLocationsResponse(
                Location.entries.map {
                    MeetingTeamLocationDto(it.name, it.value, it.isCapitalArea)
                }
            )
        }

        @JvmStatic
        @JvmName("getLocationInstance")
        fun getInstance(): MeetingTeamGetLocationsResponse {
            return instance
        }
    }

}
