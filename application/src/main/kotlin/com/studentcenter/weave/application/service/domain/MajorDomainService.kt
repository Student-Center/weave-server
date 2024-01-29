package com.studentcenter.weave.application.service.domain

import com.studentcenter.weave.domain.entity.Major
import java.util.*

interface MajorDomainService {

    fun findAll(univId: UUID): List<Major>

}
