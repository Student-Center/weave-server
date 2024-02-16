package com.studentcenter.weave.bootstrap.university.controller

import com.studentcenter.weave.application.university.port.inbound.MajorFindAllByUniversityUsecase
import com.studentcenter.weave.application.university.port.inbound.UniversityFindAllUsecase
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByNameUsecase
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
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
    private val universityFindAllUsecase: UniversityFindAllUsecase,
    private val majorFindAllByUniversityUsecase: MajorFindAllByUniversityUsecase,
    private val universityGetByIdUsecase: UniversityGetByIdUsecase,
    private val universityGetByNameUsecase: UniversityGetByNameUsecase,
) : UnivApi {

    override fun findAll(): UniversitiesResponse {
        val result = universityFindAllUsecase.invoke()
        return UniversitiesResponse.from(result.universities)
    }

    override fun getAllMajorByUniv(@PathVariable univId: UUID): MajorsResponse {
        val result = majorFindAllByUniversityUsecase.invoke(
            MajorFindAllByUniversityUsecase.Command(univId)
        )
        return MajorsResponse.from(result.majors)
    }

    override fun get(id: UUID): UniversityResponse {
        return universityGetByIdUsecase
            .invoke(id)
            .let { UniversityResponse.from(it) }
    }

    override fun getByName(name: String): UniversityResponse {
        val result = universityGetByNameUsecase.invoke(
            UniversityGetByNameUsecase.Command(UniversityName(name))
        )
        return UniversityResponse.from(result.university)
    }

}
