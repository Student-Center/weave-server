package com.studentcenter.weave.bootstrap.adapter.dto

import com.studentcenter.weave.domain.vo.UniversityName
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID


@Schema(
    name = "University domain response",
    description = "특정 대학 정보를 반환합니다",
)
data class UniversityResponse(
    val id: UUID,
    val name: UniversityName,
    val domainAddress: String,
    val logoAddress: String,
)
