package com.studentcenter.weave.bootstrap.integration

import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import io.kotest.core.annotation.DisplayName

@DisplayName("UserSilDomainService 통합 테스트")
class UserSilDomainServiceIntegrationTest(
    private val userSilDomainService: UserSilDomainService,
) : IntegrationTestDescribeSpec({

    describe("유저 실 증가 동시성 테스트") {
        context("without lock") {
            it("동시성 테스트") {

            }
        }

        context("with lock") {
            it("동시성 테스트") {

            }
        }

    }
})
