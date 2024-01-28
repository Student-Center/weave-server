package com.studentcenter.weave.support.security.jwt.util

import com.auth0.jwk.Jwk
import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.security.jwt.exception.JwtExceptionType
import com.studentcenter.weave.support.security.jwt.vo.JwtClaims
import java.net.URL
import java.security.interfaces.ECPublicKey
import java.security.interfaces.RSAPublicKey
import java.util.concurrent.TimeUnit

object JwtTokenProvider {

    fun createToken(
        jwtClaims: JwtClaims,
        secret: String,
    ): String {
        return JWT.create().withJWTId(jwtClaims.jti).withSubject(jwtClaims.sub)
            .withIssuer(jwtClaims.iss).withAudience(*jwtClaims.aud?.toTypedArray() ?: arrayOf())
            .withIssuedAt(jwtClaims.iat).withNotBefore(jwtClaims.nbf).withExpiresAt(jwtClaims.exp)
            .withPayload(jwtClaims.customClaims).sign(Algorithm.HMAC256(secret))
    }

    fun verifyToken(
        token: String,
        secret: String,
    ): Result<JwtClaims> {
        val algorithm: Algorithm = Algorithm.HMAC256(secret)
        return verifyToken(token, algorithm)
    }

    fun verifyJwksBasedToken(
        token: String,
        jwksUri: URL,
    ): Result<JwtClaims> {
        val provider = JwkProviderBuilder(jwksUri).cached(10, 60 * 24, TimeUnit.HOURS)
            .rateLimited(10, 1, TimeUnit.MINUTES).build()
        val jwt: DecodedJWT = decodeToken(token)
        val jwk: Jwk = provider[jwt.keyId]
        val algorithm: Algorithm = extractAlgorithm(jwk)
        return verifyToken(token, algorithm)
    }

    private fun extractAlgorithm(jwk: Jwk): Algorithm {
        return when (jwk.algorithm) {
            "ES256" -> Algorithm.ECDSA256(jwk.publicKey as ECPublicKey, null)
            "ES384" -> Algorithm.ECDSA384(jwk.publicKey as ECPublicKey, null)
            "ES512" -> Algorithm.ECDSA512(jwk.publicKey as ECPublicKey, null)
            "RS256" -> Algorithm.RSA256(jwk.publicKey as RSAPublicKey, null)
            "RS384" -> Algorithm.RSA384(jwk.publicKey as RSAPublicKey, null)
            "RS512" -> Algorithm.RSA512(jwk.publicKey as RSAPublicKey, null)
            else -> throw IllegalArgumentException("지원되지 않는 알고리즘입니다.")
        }
    }

    private fun verifyToken(
        token: String,
        algorithm: Algorithm,
    ): Result<JwtClaims> {
        return runCatching {
            JWT.require(algorithm).build().verify(token)
        }.mapCatching { claims ->
            JwtClaims.from(claims)
        }.onFailure { exception ->
            handleException(exception)
        }
    }

    private fun decodeToken(token: String): DecodedJWT {
        return runCatching {
            JWT.decode(token)
        }.getOrElse { exception ->
            handleException(exception)
        }
    }

    private fun handleException(ex: Throwable): Nothing {
        exceptionHandlerList.first { it.first.isAssignableFrom(ex::class.java) }.second.invoke(ex)
    }

    private val exceptionHandlerList = listOf<Pair<Class<out Throwable>, (Throwable) -> Nothing>>(
        JWTDecodeException::class.java to {
            throw CustomException(
                JwtExceptionType.JWT_DECODE_EXCEPTION, "잘못된 토큰 형식입니다."
            )
        },
        TokenExpiredException::class.java to {
            throw CustomException(
                JwtExceptionType.JWT_EXPIRED_EXCEPTION, "만료된 토큰입니다."
            )
        },
        Throwable::class.java to { e ->
            throw CustomException(
                JwtExceptionType.JWT_VERIFICATION_EXCEPTION, e.message ?: "토큰 검증에 실패했습니다."
            )
        },
    )

}
