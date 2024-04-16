package com.studentcenter.weave.domain.university.entity

import com.studentcenter.weave.domain.common.DomainEntity
import com.studentcenter.weave.domain.university.vo.MajorName
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class Major(
    override val id: UUID = UuidCreator.create(),
    val univId: UUID,
    val name: MajorName,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) : DomainEntity {


    companion object {

        fun create(
            univId: UUID,
            name: MajorName,
        ): Major {
            return Major(univId = univId, name = name)
        }
    }

}
