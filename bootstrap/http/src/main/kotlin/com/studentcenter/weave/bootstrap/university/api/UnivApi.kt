package com.studentcenter.weave.bootstrap.university.api

import com.studentcenter.weave.bootstrap.university.dto.MajorsResponse
import com.studentcenter.weave.bootstrap.university.dto.UniversitiesResponse
import com.studentcenter.weave.bootstrap.university.dto.UniversityResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*


@Tag(name = "Univ", description = "University API")
@RequestMapping("/api/univ", produces = ["application/json;charset=utf-8"])
interface UnivApi {

    @Operation(summary = "Find all university")
    @GetMapping
    fun findAll(): UniversitiesResponse

    @Operation(summary = "Get all major by university")
    @GetMapping("/{univId}/majors")
    fun getAllMajorByUniv(@PathVariable univId: UUID): MajorsResponse

    @Operation(summary = "Get university by id")
    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): UniversityResponse

    @Operation(summary = "get university by name")
    @GetMapping("/name/{name}")
    fun getByName(@PathVariable(required = true) name: String): UniversityResponse

}
