package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.outbound.UniversityRepositorySpy
import com.studentcenter.weave.application.university.service.domain.impl.UniversityDomainServiceImpl
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.domain.user.entity.UniversityFixtureFactory
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class UniversityFindAllApplicationServiceTest : DescribeSpec({

    val universityRepository = UniversityRepositorySpy()
    val universityDomainService = UniversityDomainServiceImpl(universityRepository)
    val sut = UniversityFindAllApplicationService(universityDomainService)

    afterTest {
        universityRepository.clear()
    }

    describe("대학 전체 조회 유스케이스") {
        context("저장된 대학이 없다면") {
            it("빈 리스트를 반환한다.") {
                // arrange

                // act
                val result = sut.invoke()

                // assert
                result.universities.size shouldBeEqual 0
            }
        }

        context("대학이 있다면") {
            // arrange
            val expectedUniversities = listOf(
                UniversityFixtureFactory.create(name = UniversityName("위브대학교1")),
                UniversityFixtureFactory.create(name = UniversityName("위브대학교2")),
                UniversityFixtureFactory.create(name = UniversityName("위브대학교3")),
                UniversityFixtureFactory.create(name = UniversityName("위브대학교4")),
            )

            universityRepository.saveAll(expectedUniversities)

            it("모두 조회되어야 한다.") {

                // act
                val result = sut.invoke()

                // assert
                result.universities.size shouldBeEqual expectedUniversities.size
            }
        }
    }


})
