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
    // FIXME(cache): cache layer 도입 시 사용법에 맞춰 적용하기 + Guava 같은 library 이용해서 만료, 방출 정책 적용
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
