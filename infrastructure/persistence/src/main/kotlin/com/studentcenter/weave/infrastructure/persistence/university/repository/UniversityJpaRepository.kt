package com.studentcenter.weave.infrastructure.persistence.university.repository

import com.studentcenter.weave.infrastructure.persistence.university.entity.UniversityJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UniversityJpaRepository : JpaRepository<UniversityJpaEntity, UUID> {
    fun findByName(value: String): UniversityJpaEntity?

}
