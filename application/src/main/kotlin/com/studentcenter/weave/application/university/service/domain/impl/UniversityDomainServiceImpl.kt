package com.studentcenter.weave.application.university.service.domain.impl

import com.studentcenter.weave.application.university.port.outbound.UniversityRepository
import com.studentcenter.weave.application.university.service.domain.UniversityDomainService
import com.studentcenter.weave.domain.university.entity.University
import org.springframework.stereotype.Service
import java.util.*

@Service
class UniversityDomainServiceImpl(
    private val universityRepository: UniversityRepository,
) : UniversityDomainService {

    override fun findAll(): List<University> {
        return universityRepository.findAll()
    }

    override fun getById(id: UUID): University {
        return universityRepository.getById(id)
    }

}
