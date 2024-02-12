package com.studentcenter.weave.application.university.service.domain

import com.studentcenter.weave.domain.university.entity.Major
import java.util.*

interface MajorDomainService {

    fun getById(id: UUID): Major

    fun findAll(univId: UUID): List<Major>

}
