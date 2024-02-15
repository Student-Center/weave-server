package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.MajorFindAllByUniversityUsecase
import com.studentcenter.weave.application.university.service.domain.MajorDomainService
import org.springframework.stereotype.Service

@Service
class MajorFindAllByUniversityApplicationService(
    private val majorDomainService: MajorDomainService,
) : MajorFindAllByUniversityUsecase {

    override fun invoke(command: MajorFindAllByUniversityUsecase.Command): MajorFindAllByUniversityUsecase.Result {
        return majorDomainService.findAll(univId = command.univId)
            .let { MajorFindAllByUniversityUsecase.Result(it) }
    }

}
