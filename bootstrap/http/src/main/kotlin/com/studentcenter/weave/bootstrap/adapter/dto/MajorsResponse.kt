package com.studentcenter.weave.bootstrap.adapter.dto

import io.swagger.v3.oas.annotations.media.Schema


@Schema(
    name = "All Major Response",
    description = "특정 대학교의 모든 학과 정보를 반환합니다",
)
data class MajorsResponse(
    val majors: List<String>
)
