package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

object UniversityFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        name: UniversityName = UniversityName("위브대학교"),
        displayName: String = "위브대",
        domainAddress: String = "weave.ac.kr",
        logoAddress: String? = null,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now(),
    ): University {
        return University(
            id = id,
            name = name,
            displayName = displayName,
            domainAddress = domainAddress,
            logoAddress = logoAddress,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

}
