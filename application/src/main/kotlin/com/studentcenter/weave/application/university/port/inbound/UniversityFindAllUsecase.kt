package com.studentcenter.weave.application.university.port.inbound

import com.studentcenter.weave.domain.university.entity.University

fun interface UniversityFindAllUsecase {

    fun invoke(): Result

    data class Result(val universities: List<University>)
}
