package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.university.port.outbound.MajorRepositorySpy
import com.studentcenter.weave.application.university.port.outbound.UniversityRepositorySpy
import com.studentcenter.weave.application.university.service.domain.impl.MajorDomainServiceImpl
import com.studentcenter.weave.application.university.service.domain.impl.UniversityDomainServiceImpl
import com.studentcenter.weave.application.user.port.inbound.GetMyProfile
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserSilRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.service.domain.impl.UserSilDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.user.entity.MajorFixtureFactory
import com.studentcenter.weave.domain.user.entity.UniversityFixtureFactory
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserSil
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("GetMyProfileTest")
class GetMyProfileTest : DescribeSpec({

    val userRepositorySpy = UserRepositorySpy()
    val userDomainService = UserDomainServiceImpl(userRepositorySpy)

    val userSilRepositorySpy = UserSilRepositorySpy()
    val userSilDomainService = UserSilDomainServiceImpl(userSilRepositorySpy)

    val universityRepository = UniversityRepositorySpy()
    val universityDomainService = UniversityDomainServiceImpl(universityRepository)

    val majorRepository = MajorRepositorySpy()
    val majorDomainService = MajorDomainServiceImpl(majorRepository)

    val sut = GetMyProfileService(
        userDomainService = userDomainService,
        userSilDomainService = userSilDomainService,
        universityDomainService = universityDomainService,
        majorDomainService = majorDomainService,
    )

    afterEach {
        SecurityContextHolder.clearContext()
        userRepositorySpy.clear()
        userSilRepositorySpy.clear()
        universityRepository.clear()
        majorRepository.clear()
    }

    describe("UserGetMyProfileApplicationService") {
        context("사용자가 로그인상태일때") {
            it("사용자의 프로필 정보를 응답한다.") {
                // arrange
                val user: User = UserFixtureFactory.create()
                val userAuthentication = UserAuthenticationFixtureFactory.create(user)
                val userSecurityContext = UserSecurityContext(userAuthentication)
                SecurityContextHolder.setContext(userSecurityContext)
                userRepositorySpy.save(user)
                userSilRepositorySpy.save(UserSil.create(user.id))
                val expectedUniversity = UniversityFixtureFactory.create(id = user.universityId)
                universityRepository.saveAll(listOf(expectedUniversity))
                val expectedMajor = MajorFixtureFactory.create(id = user.majorId)
                majorRepository.saveAll(listOf(expectedMajor))

                // act
                val result: GetMyProfile.Result = sut.invoke()

                // assert
                result.id shouldBe user.id
                result.nickname shouldBe user.nickname
                result.birthYear shouldBe user.birthYear
                result.avatar shouldBe user.avatar
                result.mbti shouldBe user.mbti
                result.animalType shouldBe user.animalType
                result.height shouldBe user.height
                result.universityName shouldBe expectedUniversity.name
                result.majorName shouldBe expectedMajor.name
            }
        }
    }

})
