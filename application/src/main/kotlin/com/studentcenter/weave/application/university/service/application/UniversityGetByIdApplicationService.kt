package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.university.service.domain.UniversityDomainService
import org.springframework.stereotype.Service

@Service
class UniversityGetByIdApplicationService(
    private val universityDomainService: UniversityDomainService,
) : UniversityGetByIdUsecase {

    override fun invoke(command: UniversityGetByIdUsecase.Command): UniversityGetByIdUsecase.Result {
        return universityDomainService.getById(command.id)
            .let { UniversityGetByIdUsecase.Result(it) }
    }

}
