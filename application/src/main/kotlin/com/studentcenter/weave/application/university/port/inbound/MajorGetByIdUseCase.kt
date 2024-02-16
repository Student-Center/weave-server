package com.studentcenter.weave.application.university.port.inbound

import com.studentcenter.weave.domain.university.entity.Major
import java.util.*

fun interface MajorGetByIdUseCase {

    fun invoke(id: UUID): Major

}
