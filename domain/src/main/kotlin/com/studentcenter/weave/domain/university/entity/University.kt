package com.studentcenter.weave.domain.university.entity

import com.studentcenter.weave.domain.common.AggregateRoot
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class University(
    override val id: UUID = UuidCreator.create(),
    val name: UniversityName,
    val displayName: String,
    val domainAddress: String,
    val logoAddress: String?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) : AggregateRoot {


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
                displayName = name.value
            )
        }
    }

}
