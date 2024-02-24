package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.university.service.domain.UniversityDomainService
import com.studentcenter.weave.domain.university.entity.University
import org.springframework.stereotype.Service
import java.util.*

@Service
class UniversityGetByIdApplicationService(
    private val universityDomainService: UniversityDomainService,
) : UniversityGetByIdUsecase {
    val univCache = HashMap<UUID, University>(128)

    override fun invoke(id: UUID): University {
        if (univCache.contains(id).not()) {
            val univ = universityDomainService.getById(id)
            univCache[univ.id] = univ
        }
        val university = univCache[id]!!
        return university
    }

}
