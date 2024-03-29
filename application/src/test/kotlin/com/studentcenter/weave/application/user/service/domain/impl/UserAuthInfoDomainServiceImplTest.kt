package com.studentcenter.weave.application.user.service.domain.impl

import com.studentcenter.weave.application.user.port.outbound.UserAuthInfoRepositorySpy
import com.studentcenter.weave.domain.user.entity.UserAuthInfoFixtureFactory
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

@DisplayName("UserAuthInfoDomainServiceImpl")
class UserAuthInfoDomainServiceImplTest : DescribeSpec({

    val userAuthInfoRepositorySpy = UserAuthInfoRepositorySpy()
    val sut = UserAuthInfoDomainServiceImpl(userAuthInfoRepositorySpy)

    afterTest {
        userAuthInfoRepositorySpy.clear()
    }

    describe("findByEmail") {

        context("해당 이메일로 가입한 유저 인증 정보가 존재하면") {

            val userAuthInfoFixture = UserAuthInfoFixtureFactory.create()
            userAuthInfoRepositorySpy.save(userAuthInfoFixture)

            it("해당 유저 인증 정보를 반환한다.") {
                // arrange
                val email = userAuthInfoFixture.email

                // act
                val actual = sut.findByEmail(email)

                // assert
                actual shouldBe userAuthInfoFixture
            }
        }

        context("해당 이메일로 가입한 유저 인증 정보가 존재하지 않으면") {

            it("null을 반환한다.") {
                // arrange
                val email = UserAuthInfoFixtureFactory.create().email

                // act
                val actual = sut.findByEmail(email)

                // assert
                actual shouldBe null
            }
        }

    }


})
