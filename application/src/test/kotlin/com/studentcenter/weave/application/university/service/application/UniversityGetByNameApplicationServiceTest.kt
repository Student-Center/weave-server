package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.UniversityGetByNameUsecase
import com.studentcenter.weave.application.university.port.outbound.UniversityRepositorySpy
import com.studentcenter.weave.application.university.service.domain.impl.UniversityDomainServiceImpl
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.domain.user.entity.UniversityFixtureFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class UniversityGetByNameApplicationServiceTest : DescribeSpec({

    val universityRepository = UniversityRepositorySpy()
    val universityDomainService = UniversityDomainServiceImpl(universityRepository)
    val sut = UniversityGetByNameApplicationService(universityDomainService)

    afterTest {
        universityRepository.clear()
    }

    describe("대학 단일 조회 유스케이스") {
        context("저장된 대학이 없다면") {
            it("예외가 발생한다.") {
                // arrange
                val univName = UniversityName("없는대학교")
                val command = UniversityGetByNameUsecase.Command(univName)

                // act & assert
                shouldThrow<RuntimeException> { sut.invoke(command) }
            }
        }

        context("대학이 있다면") {
            val expectedUniversity = UniversityFixtureFactory.create()
            universityRepository.saveAll(listOf(expectedUniversity))

            it("조회되어야 한다.") {
                // arrange
                val command = UniversityGetByNameUsecase.Command(expectedUniversity.name)

                // act
                val result = sut.invoke(command)

                // assert
                result.university shouldBeEqual expectedUniversity
            }
        }
    }

})
