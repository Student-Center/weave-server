package com.studentcenter.weave.bootstrap.university.controller

import com.studentcenter.weave.application.university.port.inbound.MajorFindAllByUnversityUsecase
import com.studentcenter.weave.application.university.port.inbound.UniversityFindAllUsecase
import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.bootstrap.university.api.UnivApi
import com.studentcenter.weave.bootstrap.university.dto.MajorsResponse
import com.studentcenter.weave.bootstrap.university.dto.UniversitiesResponse
import com.studentcenter.weave.bootstrap.university.dto.UniversityResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class UnivRestController(
    private val universityFindAllUsecase: UniversityFindAllUsecase,
    private val majorFindAllByUnversityUsecase: MajorFindAllByUnversityUsecase,
    private val universityGetByIdUsecase: UniversityGetByIdUsecase,
) : UnivApi {

    override fun findAll(): UniversitiesResponse {
        val result = universityFindAllUsecase.invoke()
        return UniversitiesResponse.from(result.universities)
    }

    override fun getAllMajorByUniv(@PathVariable univId: UUID): MajorsResponse {
        val result = majorFindAllByUnversityUsecase.invoke(
            MajorFindAllByUnversityUsecase.Command(univId)
        )
        return MajorsResponse.from(result.majors)
    }

    override fun get(id: UUID): UniversityResponse {
        val result = universityGetByIdUsecase.invoke(
            UniversityGetByIdUsecase.Command(id)
        )
        return UniversityResponse.from(result.university)
    }

}
