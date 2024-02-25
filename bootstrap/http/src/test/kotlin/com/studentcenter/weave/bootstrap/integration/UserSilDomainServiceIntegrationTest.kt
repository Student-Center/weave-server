package com.studentcenter.weave.bootstrap.integration

import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.domain.user.entity.UserSil
import com.studentcenter.weave.support.common.uuid.UuidCreator
import io.kotest.core.annotation.DisplayName
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@DisplayName("UserSilDomainService 통합 테스트")
class UserSilDomainServiceIntegrationTest(
    private val userSilDomainService: UserSilDomainService,
) : IntegrationTestDescribeSpec({

    describe("유저 실 증가 동시성 테스트") {
        context("분산락 적용") {
            it("동시성 테스트") {
                // arrange
                val userId = UuidCreator.create()
                userSilDomainService.create(userId)

                val threadCount = 10
                val incrementAmount = 10L

                // act
                runBlocking {
                    repeat(threadCount) {
                        launch(Dispatchers.Default) {
                            userSilDomainService.incrementByUserId(userId, incrementAmount)
                        }
                    }
                }

                // assert
                val userSil: UserSil = userSilDomainService.getByUserId(userId)
                userSil.amount shouldBe incrementAmount * threadCount
            }
        }
    }

})
