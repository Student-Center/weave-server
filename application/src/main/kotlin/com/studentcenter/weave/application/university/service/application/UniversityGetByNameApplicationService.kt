package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.UniversityGetByNameUsecase
import com.studentcenter.weave.application.university.service.domain.UniversityDomainService
import org.springframework.stereotype.Service

@Service
class UniversityGetByNameApplicationService(
    private val universityDomainService: UniversityDomainService,
) : UniversityGetByNameUsecase {

    override fun invoke(command: UniversityGetByNameUsecase.Command): UniversityGetByNameUsecase.Result {
        return universityDomainService.getByName(command.name)
            .let { UniversityGetByNameUsecase.Result(it) }
    }

}
