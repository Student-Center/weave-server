package com.studentcenter.weave.application.university.port.inbound

import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.university.vo.UniversityName

fun interface UniversityGetByNameUsecase {

    fun invoke(command: Command): Result

    data class Command(val name: UniversityName)

    data class Result(val university: University)
}
