package com.studentcenter.weave.support.security.jwt.util

import com.auth0.jwt.JWT
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.security.jwt.exception.JwtExceptionType
import com.studentcenter.weave.support.security.jwt.vo.JwtClaims
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import java.util.*

class JwtTokenProviderTest : DescribeSpec({

    describe("createToken(jwksClaims, secret)") {

        context("유효한 secret key가 전달되면") {

            val secret = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabc"

            it("해당 secret으로 서명된 토큰을 발급한다.") {
                // arrange
                val claims = JwtClaims {
                    registeredClaims {
                        sub = "sub"
                        aud = listOf("aud1", "aud2")
                    }
                    customClaims {
                        this["key"] = "value"
                        this["list"] = listOf("a", "b", "c")
                    }
                }

                // act
                val token: String = JwtTokenProvider.createToken(
                    jwtClaims = claims,
                    secret = secret,
                )

                // assert
                val body = JWT.decode(token)
                val jwtClaims = JwtClaims.from(body)

                jwtClaims.sub shouldBe "sub"
                jwtClaims.aud shouldBe setOf("aud1", "aud2")
                jwtClaims.customClaims["key"] shouldBe "value"
                jwtClaims.customClaims["list"] shouldBe listOf("a", "b", "c")
            }
        }

    }

    describe("verifyToken(token, secret)") {

        context("유효한 secret키가 전달되면") {

            val secret = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabc"

            it("해당 secret으로 서명된 토큰을 복호화한다.") {
                // arrange
                val claims = JwtClaims {
                    registeredClaims {
                        sub = "sub"
                        aud = listOf("aud1", "aud2")
                    }
                    customClaims {
                        this["key"] = "value"
                        this["list"] = listOf("a", "b", "c")
                    }
                }
                val token: String = JwtTokenProvider.createToken(
                    jwtClaims = claims,
                    secret = secret,
                )

                // act
                val result = runCatching {
                    JwtTokenProvider.verifyToken(token, secret)
                }.exceptionOrNull()

                // assert
                result shouldBe null
            }
        }

        context("토큰이 서명된 secret과 다른 secret이 주어지면") {

            val secret = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabc"
            val anotherKey = "testtestetesttestetesttestetesttestetesttestetesttestetestteste"

            it("BadRequestException 을 발생시킨다.") {
                // arrange
                val token: String = JwtTokenProvider.createToken(
                    jwtClaims = JwtClaims {
                        registeredClaims {
                            sub = "sub"
                            aud = listOf("aud1", "aud2")
                        }
                        customClaims {
                            this["key"] = "value"
                        }
                    },
                    secret = secret,
                )

                // act
                val result = runCatching {
                    JwtTokenProvider.verifyToken(token, anotherKey)
                }.exceptionOrNull()

                // assert
                result.shouldBeInstanceOf<CustomException>()
                result.type shouldBe JwtExceptionType.JWT_DECODE_EXCEPTION
            }
        }

        context("만료된 토큰이 주어지면") {

            val secret = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabc"

            it("CustomException 예외를 발생시킨다") {
                // arrange
                val token: String = JwtTokenProvider.createToken(
                    jwtClaims = JwtClaims {
                        registeredClaims {
                            sub = "sub"
                            aud = listOf("aud1", "aud2")
                            exp = Date.from(Date().toInstant().minusSeconds(1))
                        }
                        customClaims {
                            this["key"] = "value"
                        }
                    },
                    secret = secret,
                )

                // act
                val result = runCatching {
                    JwtTokenProvider.verifyToken(token, secret)
                }.exceptionOrNull()

                // assert
                result.shouldBeInstanceOf<CustomException>()
                result.type shouldBe JwtExceptionType.JWT_EXPIRED_EXCEPTION
            }
        }
    }

})
