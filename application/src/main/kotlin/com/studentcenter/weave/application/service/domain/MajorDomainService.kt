package com.studentcenter.weave.application.service.domain

import com.studentcenter.weave.domain.university.entity.Major
import java.util.*

interface MajorDomainService {

    fun findAll(univId: UUID): List<Major>

}
