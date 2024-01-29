package com.studentcenter.weave.application.port.outbound

import com.studentcenter.weave.domain.entity.Major
import java.util.*

interface MajorRepository {

    fun findAllByUnivId(univId: UUID): List<Major>

}
