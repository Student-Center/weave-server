package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.MajorFindAllByUnversityUsecase
import com.studentcenter.weave.application.university.service.domain.MajorDomainService
import org.springframework.stereotype.Service

@Service
class MajorFindAllByUniversityApplicationService(
    private val majorDomainService: MajorDomainService,
) : MajorFindAllByUnversityUsecase {

    override fun invoke(command: MajorFindAllByUnversityUsecase.Command): MajorFindAllByUnversityUsecase.Result {
        val majors = majorDomainService.findAll(univId = command.univId)
        return MajorFindAllByUnversityUsecase.Result(majors)
    }

}
