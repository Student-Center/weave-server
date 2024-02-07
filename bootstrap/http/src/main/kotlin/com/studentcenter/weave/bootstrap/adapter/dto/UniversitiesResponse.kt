package com.studentcenter.weave.bootstrap.adapter.dto

import com.studentcenter.weave.domain.entity.University
import com.studentcenter.weave.domain.vo.UniversityName
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*


@Schema(
    name = "Multi University Response",
    description = "조회된 모든 대학교를 반환합니다",
)
data class UniversitiesResponse(
    val universities: List<UniversityDto>
) {

    data class UniversityDto(
        val id: UUID,
        val name: UniversityName,
        val domainAddress: String,
        val logoAddress: String,
    )

    companion object {
        fun from(domains: List<University>) = UniversitiesResponse(
            domains.map { UniversityDto(it.id, it.name, it.domainAddress, it.logoAddress) }
        )
    }
}
