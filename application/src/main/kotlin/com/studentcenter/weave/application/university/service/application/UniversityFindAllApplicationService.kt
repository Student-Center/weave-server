package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.UniversityFindAllUsecase
import com.studentcenter.weave.application.university.service.domain.UniversityDomainService
import org.springframework.stereotype.Service

@Service
class UniversityFindAllApplicationService(
    private val universityDomainService: UniversityDomainService,
) : UniversityFindAllUsecase {

    override fun invoke(): UniversityFindAllUsecase.Result {
        val universities = universityDomainService.findAll()
        return UniversityFindAllUsecase.Result(universities)
    }

}
