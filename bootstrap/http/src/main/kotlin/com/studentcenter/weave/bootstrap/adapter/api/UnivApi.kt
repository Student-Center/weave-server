package com.studentcenter.weave.bootstrap.adapter.api

import com.studentcenter.weave.bootstrap.adapter.dto.DomainAddressResponse
import com.studentcenter.weave.bootstrap.adapter.dto.MajorsResponse
import com.studentcenter.weave.bootstrap.adapter.dto.UniversitiesResponse
import com.studentcenter.weave.domain.vo.UniversityName
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


@Tag(name = "Univ", description = "University API")
@RequestMapping("/api/univ", produces = ["application/json;charset=utf-8"])
interface UnivApi {

    @Operation(summary = "Find all university")
    @GetMapping
    fun findAll(): UniversitiesResponse

    @Operation(summary = "Get all major by university")
    @GetMapping("/{univName}/majors")
    fun getAllMajorByUniv(@PathVariable univName: UniversityName): MajorsResponse

    @Operation(summary = "Get domain address by university")
    @GetMapping("/{univName}/domain-address")
    fun getDomainAddressByUniv(@PathVariable univName: UniversityName): DomainAddressResponse

}
