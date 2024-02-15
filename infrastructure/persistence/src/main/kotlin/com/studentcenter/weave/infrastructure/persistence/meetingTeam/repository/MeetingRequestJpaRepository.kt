package com.studentcenter.weave.infrastructure.persistence.meeting.repository

import com.studentcenter.weave.infrastructure.persistence.meeting.entity.MeetingRequestJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MeetingRequestJpaRepository : JpaRepository<MeetingRequestJpaEntity, UUID> {
}
