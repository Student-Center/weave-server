package com.studentcenter.weave.application.university.port.inbound

import com.studentcenter.weave.domain.university.entity.University
import java.util.*

fun interface UniversityGetByIdUsecase {

    fun invoke(command: Command): Result

    data class Command(val id: UUID)

    data class Result(val university: University)
}
