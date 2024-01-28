package com.studentcenter.weave.bootstrap.adapter.dto

import com.studentcenter.weave.domain.vo.University
import io.swagger.v3.oas.annotations.media.Schema


@Schema(
    name = "Multi University Response",
    description = "조회된 모든 대학교를 반환합니다",
)
data class UniversitiesResponse(
    val universities: List<University>
)
