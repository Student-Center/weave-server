package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.MajorGetByIdUseCase
import com.studentcenter.weave.application.university.service.domain.MajorDomainService
import com.studentcenter.weave.domain.university.entity.Major
import org.springframework.stereotype.Service
import java.util.*

@Service
class MajorGetByIdApplicationService(
    private val majorDomainService: MajorDomainService,
): MajorGetByIdUseCase {

    override fun invoke(id: UUID): Major {
        return majorDomainService.getById(id)
    }

}
