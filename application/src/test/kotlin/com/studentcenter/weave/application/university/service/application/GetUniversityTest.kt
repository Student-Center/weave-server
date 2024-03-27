package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.outbound.UniversityRepositorySpy
import com.studentcenter.weave.application.university.service.domain.impl.UniversityDomainServiceImpl
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.domain.user.entity.UniversityFixtureFactory
import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class GetUniversityTest : DescribeSpec({

    val universityRepository = UniversityRepositorySpy()
    val universityDomainService = UniversityDomainServiceImpl(universityRepository)
    val sut = GetUniversityService(universityDomainService)

    afterTest {
        universityRepository.clear()
    }

    describe("findAll") {
        context("저장된 대학이 없다면") {
            it("빈 리스트를 반환한다.") {
                // arrange

                // act
                val result = sut.findAll()

                // assert
                result.size shouldBeEqual 0
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
                val result = sut.findAll()

                // assert
                result.size shouldBeEqual expectedUniversities.size
            }
        }
    }

    describe("getById") {
        context("저장된 대학이 없다면") {
            it("예외가 발생한다.") {
                // arrange
                val univId = UuidCreator.create()

                // act & assert
                shouldThrow<RuntimeException> { sut.getById(univId) }
            }
        }

        context("대학이 있다면") {
            val univId = UuidCreator.create()
            val expectedUniversity = UniversityFixtureFactory.create(id = univId)
            universityRepository.saveAll(listOf(expectedUniversity))

            it("조회되어야 한다.") {
                // act
                val result = sut.getById(univId)

                // assert
                result shouldBeEqual expectedUniversity
            }
        }
    }

    describe("getByName") {
        context("저장된 대학이 없다면") {
            it("예외가 발생한다.") {
                // arrange
                val univName = UniversityName("없는대학교")

                // act & assert
                shouldThrow<RuntimeException> { sut.getByName(univName) }
            }
        }

        context("대학이 있다면") {
            val expectedUniversity = UniversityFixtureFactory.create()
            universityRepository.saveAll(listOf(expectedUniversity))

            it("조회되어야 한다.") {
                // act
                val result = sut.getByName(expectedUniversity.name)

                // assert
                result shouldBeEqual expectedUniversity
            }
        }
    }

})
