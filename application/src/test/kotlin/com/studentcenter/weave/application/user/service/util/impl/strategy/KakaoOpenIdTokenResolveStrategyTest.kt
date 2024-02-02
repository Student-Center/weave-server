package com.studentcenter.weave.application.user.service.util.impl.strategy

import com.studentcenter.weave.application.common.properties.OpenIdPropertiesFixtureFactory
import com.studentcenter.weave.support.security.jwt.util.JwtTokenProvider
import com.studentcenter.weave.support.security.jwt.vo.JwtClaims
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.date.after
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkObject

@DisplayName("KakaoOpenIdTokenResolveStrategy")
class KakaoOpenIdTokenResolveStrategyTest : DescribeSpec({

    val sut = KakaoOpenIdTokenResolveStrategy(
        openIdProperties = OpenIdPropertiesFixtureFactory.create(),
    )

    beforeTest {
        mockkObject(JwtTokenProvider)
        every {
            JwtTokenProvider.verifyJwksBasedToken(any(), any())
        } returns runCatching {
            JwtClaims {
                customClaims {
                    this["nickname"] = "nickname"
                    this["email"] = "test@test.com"
                }
            }
        }
    }

    afterTest {
        clearAllMocks()
    }

    describe("resolveIdToken") {
        it("idToken을 파싱해 사용자 정보를 추출한다") {
            // arrange
            val idToken = "idToken"

            // act
            val result = sut.resolveIdToken(idToken)

            // assert
            result.nickname.value shouldBe "nickname"
            result.email.value shouldBe "test@test.com"
        }
    }

})
