package com.studentcenter.weave.infrastructure.persistence.meetingTeam.repository

import com.studentcenter.weave.infrastructure.persistence.meetingTeam.entity.MeetingTeamMemberSummaryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MeetingTeamMemberSummaryJpaRepository :
    JpaRepository<MeetingTeamMemberSummaryJpaEntity, UUID> {

    fun findByMeetingTeamId(meetingTeamId: UUID): MeetingTeamMemberSummaryJpaEntity?

}
