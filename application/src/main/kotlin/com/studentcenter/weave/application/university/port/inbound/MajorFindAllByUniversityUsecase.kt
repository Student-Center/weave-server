package com.studentcenter.weave.application.university.port.inbound

import com.studentcenter.weave.domain.university.entity.Major
import java.util.*

fun interface MajorFindAllByUniversityUsecase {

    fun invoke(command: Command): Result

    data class Command(val univId: UUID)

    data class Result(val majors: List<Major>)
}
