package com.studentcenter.weave.bootstrap.university.controller

import com.studentcenter.weave.application.university.port.inbound.GetMajor
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.bootstrap.university.api.UnivApi
import com.studentcenter.weave.bootstrap.university.dto.MajorsResponse
import com.studentcenter.weave.bootstrap.university.dto.UniversitiesResponse
import com.studentcenter.weave.bootstrap.university.dto.UniversityResponse
import com.studentcenter.weave.domain.university.vo.UniversityName
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class UnivRestController(
    private val getUniversity: GetUniversity,
    private val getMajor: GetMajor,
) : UnivApi {

    override fun findAll(): UniversitiesResponse {
        return getUniversity
            .findAll()
            .let { UniversitiesResponse.from(it) }
    }

    override fun getAllMajorByUniv(@PathVariable univId: UUID): MajorsResponse {
        return getMajor
            .findAllByUniversityId(univId)
            .let { MajorsResponse.from(it) }
    }

    override fun get(id: UUID): UniversityResponse {
        return getUniversity
            .getById(id)
            .let { UniversityResponse.from(it) }
    }

    override fun getByName(name: String): UniversityResponse {
        return getUniversity
            .getByName(UniversityName(name))
            .let { UniversityResponse.from(it) }
    }

}
