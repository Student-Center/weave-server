package com.studentcenter.weave.infrastructure.persistence.university.adapter

import com.studentcenter.weave.application.university.port.outbound.UniversityRepository
import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.infrastructure.persistence.common.exception.PersistenceExceptionType
import com.studentcenter.weave.infrastructure.persistence.university.repository.UniversityJpaRepository
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Component
import java.util.*

@Component
class UniversityJpaAdapter(
    private val universityJpaRepository: UniversityJpaRepository
) : UniversityRepository {

    override fun findAll(): List<University> {
        return universityJpaRepository.findAll()
            .let { it.map { entity -> entity.toDomain() } }
    }

    override fun getById(id: UUID): University {
        return universityJpaRepository.getReferenceById(id)
            .toDomain()
    }

    override fun getByName(name: UniversityName): University {
        return universityJpaRepository.findByName(name.value)
            ?.toDomain()
            ?: throw CustomException(
                PersistenceExceptionType.RESOURCE_NOT_FOUND,
                "${name.value}의 이름을 가진 대학교를 찾을 수 없습니다."
            )
    }

}
