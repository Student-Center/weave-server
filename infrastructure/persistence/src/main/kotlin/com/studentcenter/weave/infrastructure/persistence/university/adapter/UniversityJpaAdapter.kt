package com.studentcenter.weave.infrastructure.persistence.meeting.adapter

import com.studentcenter.weave.application.university.port.outbound.UniversityRepository
import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.infrastructure.persistence.university.repository.UniversityJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class UniversityJpaAdapter(
    private val universityJpaRepository: UniversityJpaRepository
) : UniversityRepository {

    override fun findAll(): List<University> {
        val universityEntities = universityJpaRepository.findAll()
        return universityEntities.map { it.toDomain() }
    }

    override fun getById(id: UUID): University {
        val universityEntity = universityJpaRepository.getReferenceById(id)
        return universityEntity.toDomain()
    }

}
