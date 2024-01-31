package com.studentcenter.weave.application.service.util.impl

import com.studentcenter.weave.application.common.properties.JwtTokenProperties
import com.studentcenter.weave.application.port.outbound.UserRefreshTokenRepository
import com.studentcenter.weave.application.service.util.UserTokenService
import com.studentcenter.weave.application.service.util.UserTokenType
import com.studentcenter.weave.application.service.util.impl.strategy.OpenIdTokenResolveStrategy
import com.studentcenter.weave.application.service.util.impl.strategy.OpenIdTokenResolveStrategyFactory
import com.studentcenter.weave.application.vo.UserTokenClaims
import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url
import com.studentcenter.weave.support.security.jwt.util.JwtTokenProvider
import com.studentcenter.weave.support.security.jwt.vo.JwtClaims
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class UserTokenServiceImpl(
    private val jwtTokenProperties: JwtTokenProperties,
    private val userRefreshTokenRepository: UserRefreshTokenRepository,
    private val openIdTokenResolveStrategyFactory: OpenIdTokenResolveStrategyFactory
) : UserTokenService {

    override fun resolveIdToken(
        idToken: String,
        provider: SocialLoginProvider
    ): UserTokenClaims.IdToken {
        val openIdTokenResolveStrategy: OpenIdTokenResolveStrategy =
            openIdTokenResolveStrategyFactory.getStrategy(provider)
        return openIdTokenResolveStrategy.resolveIdToken(idToken)
    }

    override fun generateRegisterToken(
        email: Email,
        nickname: Nickname,
        provider: SocialLoginProvider
    ): String {
        val expirationTime: Instant = Instant
            .now()
            .plusSeconds(jwtTokenProperties.register.expireSeconds)

        val jwtClaims = JwtClaims {
            registeredClaims {
                iss = jwtTokenProperties.issuer
                exp = Date.from(expirationTime)
            }
            customClaims {
                this["type"] = UserTokenType.REGISTER_TOKEN.name
                this["email"] = email.value
                this["nickname"] = nickname.value
                this["provider"] = provider.name
            }
        }

        return JwtTokenProvider.createToken(jwtClaims, jwtTokenProperties.register.secret)
    }

    override fun resolveRegisterToken(registerToken: String): UserTokenClaims.RegisterToken {
        val jwtClaims: JwtClaims = JwtTokenProvider
            .verifyToken(registerToken, jwtTokenProperties.register.secret)
            .getOrThrow()

        return UserTokenClaims.RegisterToken(
            email = Email(jwtClaims.customClaims["email"] as String),
            nickname = Nickname(jwtClaims.customClaims["nickname"] as String),
            socialLoginProvider = SocialLoginProvider.valueOf(jwtClaims.customClaims["provider"] as String),
        )
    }

    override fun generateAccessToken(user: User): String {
        val jwtClaims = JwtClaims {
            registeredClaims {
                iss = jwtTokenProperties.issuer
                exp = Date.from(Instant.now().plusSeconds(jwtTokenProperties.access.expireSeconds))
            }
            customClaims {
                this["type"] = UserTokenType.ACCESS_TOKEN.name
                this["userId"] = user.id.toString()
                this["nickname"] = user.nickname.value
                this["email"] = user.email.value
                user.avatar?.let { this["avatar"] = it.value }
            }
        }

        return JwtTokenProvider.createToken(jwtClaims, jwtTokenProperties.access.secret)
    }

    override fun resolveAccessToken(accessToken: String): UserTokenClaims.AccessToken {
        val jwtClaims: JwtClaims = JwtTokenProvider
            .verifyToken(accessToken, jwtTokenProperties.access.secret)
            .getOrThrow()

        return UserTokenClaims.AccessToken(
            userId = UUID.fromString(jwtClaims.customClaims["userId"] as String),
            nickname = Nickname(jwtClaims.customClaims["nickname"] as String),
            email = Email(jwtClaims.customClaims["email"] as String),
            avatar = (jwtClaims.customClaims["avatar"] as String?)?.let { Url(it) },
        )
    }

    override fun generateRefreshToken(user: User): String {
        val jwtClaims = JwtClaims {
            registeredClaims {
                iss = jwtTokenProperties.issuer
                exp = Date.from(Instant.now().plusSeconds(jwtTokenProperties.refresh.expireSeconds))
            }
            customClaims {
                this["type"] = UserTokenType.REFRESH_TOKEN.name
                this["userId"] = user.id.toString()
            }
        }

        return JwtTokenProvider
            .createToken(jwtClaims, jwtTokenProperties.refresh.secret)
            .also {
                userRefreshTokenRepository.save(
                    userId = user.id,
                    refreshToken = it,
                    expirationSeconds = jwtTokenProperties.refresh.expireSeconds
                )
            }
    }

    override fun resolveRefreshToken(refreshToken: String): UserTokenClaims.RefreshToken {
        val jwtClaims: JwtClaims = JwtTokenProvider
            .verifyToken(refreshToken, jwtTokenProperties.refresh.secret)
            .getOrThrow()

        return UserTokenClaims.RefreshToken(
            userId = UUID.fromString(jwtClaims.customClaims["userId"] as String),
        )
    }
}
