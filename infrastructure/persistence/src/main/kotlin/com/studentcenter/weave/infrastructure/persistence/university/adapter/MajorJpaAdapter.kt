package com.studentcenter.weave.infrastructure.persistence.university.adapter

import com.studentcenter.weave.application.university.port.outbound.MajorRepository
import com.studentcenter.weave.domain.university.entity.Major
import com.studentcenter.weave.infrastructure.persistence.university.repository.MajorJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class MajorJpaAdapter(
    private val majorJpaRepository: MajorJpaRepository,
) : MajorRepository {
    override fun getById(id: UUID): Major {
        return majorJpaRepository.findByIdOrNull(id)?.toDomain()
            ?: throw NoSuchElementException("전공을 찾을 수 없습니다.")
    }

    override fun findAllByUnivId(univId: UUID): List<Major> {
        return majorJpaRepository.findAllByUnivId(univId = univId)
            .let { it.map { entity -> entity.toDomain() } }
    }

}
