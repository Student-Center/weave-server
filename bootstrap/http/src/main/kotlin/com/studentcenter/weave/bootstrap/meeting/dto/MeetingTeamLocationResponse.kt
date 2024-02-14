package com.studentcenter.weave.bootstrap.meeting.dto

import com.studentcenter.weave.domain.meeting.enums.Location

data class MeetingTeamLocationResponse(
    val locations: List<MeetingTeamLocationDto>
) {

    data class MeetingTeamLocationDto(
        val name: String,
        val value: String,
        val isCapitalArea: String
    )

    companion object {
        private lateinit var instance: MeetingTeamLocationResponse

        fun getInstance(): MeetingTeamLocationResponse {

            instance = MeetingTeamLocationResponse(
                Location.entries.map {
                    MeetingTeamLocationDto(it.name, it.value, it.isCapitalArea.toString())
                }
            )

            return instance
        }

    }

}
