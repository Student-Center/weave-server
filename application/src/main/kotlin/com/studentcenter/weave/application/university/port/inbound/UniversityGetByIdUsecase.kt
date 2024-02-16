package com.studentcenter.weave.application.university.port.inbound

import com.studentcenter.weave.domain.university.entity.University
import java.util.*

fun interface UniversityGetByIdUsecase {

    fun invoke(id: UUID): University

}
