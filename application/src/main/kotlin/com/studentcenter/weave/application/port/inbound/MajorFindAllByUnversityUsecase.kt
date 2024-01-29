package com.studentcenter.weave.application.port.inbound

import com.studentcenter.weave.domain.entity.Major
import java.util.*

fun interface MajorFindAllByUnversityUsecase {

    fun invoke(command: Command): Result

    data class Command(val univId: UUID)

    data class Result(val majors: List<Major>)
}
