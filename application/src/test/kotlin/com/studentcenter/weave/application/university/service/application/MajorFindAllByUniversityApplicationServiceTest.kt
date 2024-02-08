package com.studentcenter.weave.application.university.service.application

import com.studentcenter.weave.application.university.port.inbound.MajorFindAllByUnversityUsecase
import com.studentcenter.weave.application.university.port.outbound.MajorRepositorySpy
import com.studentcenter.weave.application.university.service.domain.impl.MajorDomainServiceImpl
import com.studentcenter.weave.domain.university.vo.MajorName
import com.studentcenter.weave.domain.user.entity.MajorFixtureFactory
import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.equals.shouldBeEqual

class MajorFindAllByUniversityApplicationServiceTest : DescribeSpec({

    val majorRepositorySpy = MajorRepositorySpy()
    val majorDomainService = MajorDomainServiceImpl(majorRepositorySpy)
    val sut = MajorFindAllByUniversityApplicationService(majorDomainService)

    afterTest {
        majorRepositorySpy.clear()
    }

    describe("대학교 정보를 이용한 전공 조회 유스케이스") {
        context("해당 대학의 전공 과목이 하나도 없다며") {
            it("빈 리스트를 반환한다.") {
                // arrange
                val univId = UuidCreator.create()
                val command = MajorFindAllByUnversityUsecase.Command(univId)

                // act
                val result = sut.invoke(command)

                // assert
                result.majors.size shouldBeEqual 0
            }
        }

        context("해당 대학의 전공 과목이 있다면") {

            val univId = UuidCreator.create()
            val expectedMajors = listOf(
              MajorFixtureFactory.create(univId =  univId, name = MajorName("name1")),
              MajorFixtureFactory.create(univId =  univId, name = MajorName("name2")),
              MajorFixtureFactory.create(univId =  univId, name = MajorName("name3")),
            )

            majorRepositorySpy.saveAll(expectedMajors)

            it("전공과목을 반환한다.") {
                // arrange
                val command = MajorFindAllByUnversityUsecase.Command(univId)

                // act
                val result = sut.invoke(command)

                // assert
                result.majors.size shouldBeEqual expectedMajors.size
            }
        }
    }


})
