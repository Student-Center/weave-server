package com.studentcenter.weave.application.port.inbound

import com.studentcenter.weave.domain.entity.University

fun interface UniversityFindAllUsecase {

    fun invoke(): Result

    data class Result(val universities: List<University>)
    
}
