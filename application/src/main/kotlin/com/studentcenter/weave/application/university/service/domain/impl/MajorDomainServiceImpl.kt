package com.studentcenter.weave.application.university.service.domain.impl

import com.studentcenter.weave.application.university.port.outbound.MajorRepository
import com.studentcenter.weave.application.university.service.domain.MajorDomainService
import com.studentcenter.weave.domain.university.entity.Major
import org.springframework.stereotype.Service
import java.util.*

@Service
class MajorDomainServiceImpl(
    private val majorRepository: MajorRepository
) : MajorDomainService {
    override fun getById(id: UUID): Major {
        return majorRepository.getById(id)
    }

    override fun findAll(univId: UUID): List<Major> {
        return majorRepository.findAllByUnivId(univId = univId)
    }

}
