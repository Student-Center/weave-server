package com.studentcenter.weave.application.university.port.outbound

import com.studentcenter.weave.domain.university.entity.Major
import java.util.*

interface MajorRepository {

    fun findAllByUnivId(univId: UUID): List<Major>

}
