package com.studentcenter.weave.infrastructure.persistence.meetingTeam.repository

import com.studentcenter.weave.domain.meetingTeam.entity.MeetingTeamMemberSummary
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MeetingTeamMemberSummaryJpaRepository : JpaRepository<MeetingTeamMemberSummary, UUID> {

    fun findByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummary?

}
