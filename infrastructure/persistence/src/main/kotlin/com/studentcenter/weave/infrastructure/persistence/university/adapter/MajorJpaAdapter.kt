package com.studentcenter.weave.infrastructure.persistence.meeting.adapter

import com.studentcenter.weave.application.university.port.outbound.MajorRepository
import com.studentcenter.weave.domain.university.entity.Major
import com.studentcenter.weave.infrastructure.persistence.university.repository.MajorJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class MajorJpaAdapter(
    private val majorJpaRepository: MajorJpaRepository,
) : MajorRepository {

    override fun findAllByUnivId(univId: UUID): List<Major> {
        return majorJpaRepository.findAllByUnivId(univId = univId)
            .let { it.map { entity -> entity.toDomain() } }
    }

}
