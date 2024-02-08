package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.university.entity.Major
import com.studentcenter.weave.domain.university.vo.MajorName
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

object MajorFixtureFactory {

    fun create(
        id: UUID = UuidCreator.create(),
        univId: UUID = UuidCreator.create(),
        name: MajorName = MajorName("컴퓨터공학과"),
        createdAt: LocalDateTime = LocalDateTime.now(),
    ): Major {
        return Major(
            id = id,
            univId = univId,
            name = name,
            createdAt = createdAt,
        )
    }

}
