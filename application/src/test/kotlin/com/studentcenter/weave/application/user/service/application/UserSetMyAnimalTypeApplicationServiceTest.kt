package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.support.lock.distributedLock
import com.studentcenter.weave.application.common.security.context.UserSecurityContext
import com.studentcenter.weave.application.user.port.outbound.UserRepositorySpy
import com.studentcenter.weave.application.user.port.outbound.UserSilRepositorySpy
import com.studentcenter.weave.application.user.service.domain.impl.UserDomainServiceImpl
import com.studentcenter.weave.application.user.service.domain.impl.UserSilDomainServiceImpl
import com.studentcenter.weave.application.user.vo.UserAuthenticationFixtureFactory
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserFixtureFactory
import com.studentcenter.weave.domain.user.entity.UserSil
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.support.security.context.SecurityContextHolder
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearStaticMockk
import io.mockk.every
import io.mockk.mockkStatic

@DisplayName("UserSetMyAnimalTypeApplicationService")
class UserSetMyAnimalTypeApplicationServiceTest : DescribeSpec({

    val userRepositorySpy = UserRepositorySpy()
    val userSilRepositorySpy = UserSilRepositorySpy()
    val userDomainService = UserDomainServiceImpl(userRepositorySpy)
    val userSilDomainService = UserSilDomainServiceImpl(userSilRepositorySpy)
    val sut = UserSetMyAnimalTypeApplicationService(
        userDomainService = userDomainService,
        userSilDomainService = userSilDomainService
    )

    beforeTest {
        mockkStatic("com.studentcenter.weave.support.lock.DistributedLockKt")
        every {
            distributedLock<Any?>(any(), any(), any(), captureLambda())
        } answers {
            val lambda: () -> Any? = arg<(()-> Any?)>(3)
            lambda()
        }
    }

    afterTest {
        userRepositorySpy.clear()
        userSilRepositorySpy.clear()
        SecurityContextHolder.clearContext()
        clearStaticMockk()
    }

    describe("닮은 동물상 등록 유스케이스") {
        enumValues<AnimalType>().forEach { animalType: AnimalType ->
            context("사용자가 로그인 한 상태일때") {
                it("유저의 닮은 동물상($animalType)을 등록한다.") {
                    // arrange
                    val userFixture: User = UserFixtureFactory.create()
                    userRepositorySpy.save(userFixture)
                    userSilRepositorySpy.save(UserSil.create(userFixture.id))

                    val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)
                    SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                    // act
                    sut.invoke(animalType)

                    // assert
                    val user = userRepositorySpy.getById(userFixture.id)
                    val expected = userFixture.copy(animalType = animalType)
                    user.animalType shouldBe expected.animalType
                }
            }
        }

        context("이미 동물상이 등록된 상태이면") {
            it("실을 지급하지 않는다.") {
                // arrange
                val userFixture: User = UserFixtureFactory.create(animalType = AnimalType.CAT)
                userRepositorySpy.save(userFixture)
                userSilRepositorySpy.save(UserSil.create(userFixture.id))

                val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act
                sut.invoke(AnimalType.FOX)

                // assert
                val userSil: UserSil = userSilRepositorySpy.getByUserId(userFixture.id)
                userSil.amount shouldBe 0
            }
        }

        context("동물상을 처음 등록하면") {
            it("30 실을 지급한다.") {
                // arrange
                val userFixture: User = UserFixtureFactory.create()
                userRepositorySpy.save(userFixture)
                userSilRepositorySpy.save(UserSil.create(userFixture.id))

                val userAuthentication = UserAuthenticationFixtureFactory.create(userFixture)
                SecurityContextHolder.setContext(UserSecurityContext(userAuthentication))

                // act
                sut.invoke(AnimalType.FOX)

                // assert
                val userSil: UserSil = userSilRepositorySpy.getByUserId(userFixture.id)
                userSil.amount shouldBe 30

            }
        }

    }

})
