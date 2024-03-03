package com.studentcenter.weave.bootstrap.university.dto

import com.studentcenter.weave.domain.university.entity.University
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
        val name: String,
        val displayName: String,
        val domainAddress: String,
        val logoAddress: String?,
    )

    companion object {

        fun from(domains: List<University>) = UniversitiesResponse(
            domains.map {
                UniversityDto(
                    id = it.id,
                    name = it.name.value,
                    displayName = it.displayName,
                    domainAddress = it.domainAddress,
                    logoAddress = it.logoAddress
                )
            }
        )
    }
}
