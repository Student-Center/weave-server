package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.outbound.MajorRepositorySpy
import com.studentcenter.weave.application.university.service.domain.impl.MajorDomainServiceImpl
import com.studentcenter.weave.domain.university.vo.MajorName
import com.studentcenter.weave.domain.user.entity.MajorFixtureFactory
import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class GetMajorTest : DescribeSpec({

    val majorRepositorySpy = MajorRepositorySpy()
    val majorDomainService = MajorDomainServiceImpl(majorRepositorySpy)
    val sut = GetMajorService(majorDomainService)

    afterTest {
        majorRepositorySpy.clear()
    }

    describe("findAllByUniversityId") {
        context("해당 대학의 전공 과목이 하나도 없다면") {
            it("빈 리스트를 반환한다.") {
                // arrange
                val univId = UuidCreator.create()

                // act
                val result = sut.findAllByUniversityId(univId)

                // assert
                result.size shouldBeEqual 0
            }
        }

        context("해당 대학의 전공 과목이 있다면") {

            val univId = UuidCreator.create()
            val expectedMajors = listOf(
                MajorFixtureFactory.create(univId = univId, name = MajorName("name1")),
                MajorFixtureFactory.create(univId = univId, name = MajorName("name2")),
                MajorFixtureFactory.create(univId = univId, name = MajorName("name3")),
            )

            majorRepositorySpy.saveAll(expectedMajors)

            it("전공과목을 반환한다.") {
                // act
                val result = sut.findAllByUniversityId(univId)

                // assert
                result.size shouldBeEqual expectedMajors.size
            }
        }
    }

})
