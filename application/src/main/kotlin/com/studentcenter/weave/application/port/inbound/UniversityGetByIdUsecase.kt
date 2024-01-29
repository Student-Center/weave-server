package com.studentcenter.weave.application.port.inbound

import com.studentcenter.weave.domain.entity.University
import java.util.*

fun interface UniversityGetByIdUsecase {

    fun invoke(command: Command): Result

    data class Command(val id: UUID)

    data class Result(val university: University)
}
