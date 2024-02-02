package com.studentcenter.weave.bootstrap.adapter.dto

import com.studentcenter.weave.domain.university.entity.Major
import com.studentcenter.weave.domain.university.vo.MajorName
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*


@Schema(
    name = "All Major Response",
    description = "특정 대학교의 모든 학과 정보를 반환합니다",
)
data class MajorsResponse(
    val majors: List<MajorDto>
) {

    data class MajorDto(val id: UUID, val name: MajorName)

    companion object {
        fun from(domains: List<Major>): MajorsResponse {
            return MajorsResponse(domains.map { MajorDto(it.id, it.name) })
        }
    }
}
