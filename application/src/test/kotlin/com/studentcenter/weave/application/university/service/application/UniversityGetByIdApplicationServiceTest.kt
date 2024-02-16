package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.UniversityGetByIdUsecase
import com.studentcenter.weave.application.university.port.outbound.UniversityRepositorySpy
import com.studentcenter.weave.application.university.service.domain.impl.UniversityDomainServiceImpl
import com.studentcenter.weave.domain.user.entity.UniversityFixtureFactory
import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class UniversityGetByIdApplicationServiceTest : DescribeSpec({

    val universityRepository = UniversityRepositorySpy()
    val universityDomainService = UniversityDomainServiceImpl(universityRepository)
    val sut = UniversityGetByIdApplicationService(universityDomainService)

    afterTest {
        universityRepository.clear()
    }

    describe("대학 단일 조회 유스케이스") {
        context("저장된 대학이 없다면") {
            it("예외가 발생한다.") {
                // arrange
                val univId = UuidCreator.create()

                // act & assert
                shouldThrow<RuntimeException> { sut.invoke(univId) }
            }
        }

        context("대학이 있다면") {
            val univId = UuidCreator.create()
            val expectedUniversity = UniversityFixtureFactory.create(id = univId)
            universityRepository.saveAll(listOf(expectedUniversity))

            it("조회되어야 한다.") {
                // act
                val result = sut.invoke(univId)

                // assert
                result shouldBeEqual expectedUniversity
            }
        }
    }

})
