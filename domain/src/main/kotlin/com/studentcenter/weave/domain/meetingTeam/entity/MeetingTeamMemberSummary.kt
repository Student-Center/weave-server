package com.studentcenter.weave.domain.meetingTeam.entity

data class MeetingTeamMemberSummary(
    val id: String,
    val meetingTeamId: String,
    val teamMbti: String,
    val minAge: Int,
    val maxAge: Int,
    val createdAt: String,
)
