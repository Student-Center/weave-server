package com.studentcenter.weave.infrastructure.persistence.university.repository

import com.studentcenter.weave.infrastructure.persistence.university.entity.MajorJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MajorJpaRepository : JpaRepository<MajorJpaEntity, UUID> {
    fun findAllByUnivId(univId: UUID): List<MajorJpaEntity>
}
