package com.studentcenter.weave.bootstrap.common.security.interceptor

import com.studentcenter.weave.application.service.util.UserTokenService
import com.studentcenter.weave.application.vo.UserTokenClaimsFixtureFactory
import com.studentcenter.weave.bootstrap.common.exception.CustomExceptionHandler
import com.studentcenter.weave.bootstrap.common.security.filter.JwtAuthenticationFilter
import com.studentcenter.weave.bootstrap.controller.AuthorizationInterceptorTestController
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder

@DisplayName("AuthorizationInterceptorTest")
@WebMvcTest(
    AuthorizationInterceptorTestController::class,
    CustomExceptionHandler::class
)
class AuthorizationInterceptorTest : DescribeSpec({

    val userTokenService: UserTokenService = mockk<UserTokenService>()
    val authenticationFilter = JwtAuthenticationFilter(userTokenService)
    val sut = AuthorizationInterceptor()
    val mockMvc: MockMvc = MockMvcBuilders
        .standaloneSetup(AuthorizationInterceptorTestController())
        .setControllerAdvice(CustomExceptionHandler())
        .addFilter<StandaloneMockMvcBuilder?>(authenticationFilter)
        .addInterceptors(sut)
        .build()

    describe("AuthorizationInterceptor") {
        context("유효한 인증 토큰과 함께, @Secured 어노테이션이 붙은 메소드에 접근하면") {
            it("정상적으로 요청이 처리 된다") {
                // arrange
                every { userTokenService.resolveAccessToken(any()) } returns UserTokenClaimsFixtureFactory.createAccessTokenClaim()

                // act, assert
                mockMvc.get("/secured-method") {
                    header("Authorization", "Bearer access-token")
                }.andExpect {
                    status { isOk() }
                }
            }
        }

        context("인증 토큰 없이, @Secured 어노테이션이 붙은 메소드에 접근하면") {
            it("API-003(Unauthorized)을 응답한다") {
                // act, assert
                mockMvc.get("/secured-method").andExpect {
                    status { isBadRequest() }
                    content {
                        jsonPath("$.exceptionCode") { value("API-003") }
                    }
                }
            }
        }

        context("유효한 인증 토큰과 함께, @Secured 어노테이션이 없는 메소드에 접근하면") {
            it("정상적으로 요청이 처리 된다") {
                // arrange
                every { userTokenService.resolveAccessToken(any()) } returns UserTokenClaimsFixtureFactory.createAccessTokenClaim()

                // act, assert
                mockMvc.get("/unsecured-method") {
                    header("Authorization", "Bearer access-token")
                }.andExpect {
                    status { isOk() }
                }
            }
        }

        context("인증 토큰 없이, @Secured 어노테이션이 없는 메소드에 접근하면") {
            it("정상적으로 요청이 처리 된다") {
                // act, assert
                mockMvc.get("/unsecured-method").andExpect {
                    status { isOk() }
                }
            }
        }
    }

})
