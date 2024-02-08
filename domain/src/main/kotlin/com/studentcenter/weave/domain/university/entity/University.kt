package com.studentcenter.weave.domain.university.entity

import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class University(
    val id: UUID = UuidCreator.create(),
    val name: UniversityName,
    val domainAddress: String,
    val logoAddress: String?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {


    companion object {
        fun create(
            name: UniversityName,
            domainAddress: String,
            logoAddress: String,
        ): University {
            return University(
                name = name,
                domainAddress = domainAddress,
                logoAddress = logoAddress,
            )
        }
    }

}
