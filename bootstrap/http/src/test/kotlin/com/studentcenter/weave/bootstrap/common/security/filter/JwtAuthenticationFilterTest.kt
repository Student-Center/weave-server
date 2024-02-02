package com.studentcenter.weave.bootstrap.common.security.filter

import com.studentcenter.weave.application.user.service.util.UserTokenService
import com.studentcenter.weave.application.user.vo.UserTokenClaimsFixtureFactory
import com.studentcenter.weave.bootstrap.controller.JwtAuthenticationFilterTestController
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder


@DisplayName("JwtAuthenticationFilterTest")
@WebMvcTest(JwtAuthenticationFilterTestController::class)
class JwtAuthenticationFilterTest : DescribeSpec({

    val userTokenService: UserTokenService = mockk<UserTokenService>()
    val sut = JwtAuthenticationFilter(userTokenService)
    val mockMvc: MockMvc = MockMvcBuilders
        .standaloneSetup(JwtAuthenticationFilterTestController())
        .addFilter<StandaloneMockMvcBuilder?>(sut)
        .build()

    describe("JwtAuthenticationFilter") {
        context("Authorization Header에 access toeken을 전달하면") {
            it("UserAuthentication이 SecurityContextHolder에 저장된다") {
                // arrange
                every { userTokenService.resolveAccessToken(any()) } returns UserTokenClaimsFixtureFactory.createAccessTokenClaim()

                // act, assert
                mockMvc.get("/jwt-authentication-filter-test") {
                    header("Authorization", "Bearer access-token")
                }.andExpect {
                    status { isOk() }
                }
            }
        }

        context("Authorization Header에 access toeken을 전달하지 않으면") {
            it("UserAuthentication이 SecurityContextHolder에 저장되지 않는다") {
                // act, assert
                mockMvc.get("/jwt-authentication-filter-test").andExpect {
                    status { isUnauthorized() }
                }
            }
        }
    }

})
