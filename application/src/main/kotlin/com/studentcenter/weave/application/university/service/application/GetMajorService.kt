package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.GetMajor
import com.studentcenter.weave.application.university.service.domain.MajorDomainService
import com.studentcenter.weave.domain.university.entity.Major
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class GetMajorService(
    private val majorDomainService: MajorDomainService,
): GetMajor {

    override fun getById(id: UUID): Major {
        return majorDomainService.getById(id)
    }

    override fun findAllByUniversityId(universityId: UUID): List<Major> {
        return majorDomainService.findAllByUniversityId(universityId)
    }

}
