package com.studentcenter.weave.application.university.port.inbound

import com.studentcenter.weave.domain.university.entity.Major
import java.util.*

interface GetMajor {

    fun getById(id: UUID): Major

    fun findAllByUniversityId(universityId: UUID): List<Major>

}
