package com.studentcenter.weave.bootstrap.adapter.dto

import io.swagger.v3.oas.annotations.media.Schema


@Schema(
    name = "Domain address response",
    description = "특정 대학의 도메인 정보를 반환합니다",
)
data class DomainAddressResponse(
    val domainAddress: String
)
