package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.application.university.service.domain.UniversityDomainService
import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.university.vo.UniversityName
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class GetUniversityService(
    private val universityDomainService: UniversityDomainService,
) : GetUniversity {

    override fun findAll(): List<University> {
        return universityDomainService.findAll()
    }

    override fun getById(id: UUID): University {
        return universityDomainService.getById(id)
    }

    override fun getByName(name: UniversityName): University {
        return universityDomainService.getByName(name)
    }

}
