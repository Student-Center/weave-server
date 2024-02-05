package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthentication
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("UserSetMyAnimalTypeApplicationService")
class UserSetMyAnimalTypeApplicationServiceTest : DescribeSpec({

    val userRepositorySpy = UserRepositorySpy()
    val userDomainService = UserDomainServiceImpl(userRepositorySpy)
    val sut = UserSetMyAnimalTypeApplicationService(userDomainService)

    describe("닮은 동물상 등록 유스케이스") {
        enumValues<AnimalType>().forEach { animalType: AnimalType ->
            context("사용자가 로그인 한 상태일때") {
                it("유저의 닮은 동물상($animalType)을 등록한다.") {
                    // arrange
                    val userFixture: User = UserFixtureFactory.create()
                    userRepositorySpy.save(userFixture)

                    val userAuthentication = UserAuthentication(
                        userId = userFixture.id,
                        email = userFixture.email,
                        nickname = userFixture.nickname,
                        avatar = userFixture.avatar
                    )
                    SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                    // act
                    sut.invoke(animalType)

                    // assert
                    val user = userRepositorySpy.findById(userFixture.id)
                    val expected = userFixture.copy(animalType = animalType)
                    user shouldBe expected
                }
            }
        }
    }

})
