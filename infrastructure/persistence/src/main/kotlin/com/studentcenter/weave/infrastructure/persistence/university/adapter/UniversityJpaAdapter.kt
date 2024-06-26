package com.studentcenter.weave.infrastructure.persistence.university.adapter

import com.studentcenter.weave.application.university.port.outbound.UniversityRepository
import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceException
import com.studentcenter.weave.infrastructure.persistence.university.repository.UniversityJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class UniversityJpaAdapter(
    private val universityJpaRepository: UniversityJpaRepository,
) : UniversityRepository {

    override fun findAll(): List<University> {
        return universityJpaRepository.findAll()
            .let { it.map { entity -> entity.toDomain() } }
    }

    override fun getById(id: UUID): University {
        return universityJpaRepository.findByIdOrNull(id)?.toDomain()
            ?: throw PersistenceException.ResourceNotFound("University(id: $id)를 찾을 수 없습니다.")
    }

    override fun getByName(name: UniversityName): University {
        return universityJpaRepository.findByName(name.value)
            ?.toDomain()
            ?: throw PersistenceException.ResourceNotFound("University(name=${name.value})를 찾을 수 없습니다.")
    }

}
