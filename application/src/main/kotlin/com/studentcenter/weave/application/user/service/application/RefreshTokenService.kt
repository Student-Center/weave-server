package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.AuthException
import com.studentcenter.weave.application.user.port.inbound.RefreshToken
import com.studentcenter.weave.application.user.port.outbound.UserRefreshTokenRepository
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.util.UserTokenService
import com.studentcenter.weave.application.user.vo.UserTokenClaims
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.support.security.jwt.exception.JwtException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshTokenService(
    private val userDomainService: UserDomainService,
    private val userTokenService: UserTokenService,
    private val userRefreshTokenRepository: UserRefreshTokenRepository,
) : RefreshToken {

    @Transactional
    override fun invoke(command: RefreshToken.Command): RefreshToken.Result {
        lateinit var refreshToken: UserTokenClaims.RefreshToken
        try {
            refreshToken = userTokenService.resolveRefreshToken(command.refreshToken)
        } catch (e: JwtException.Expired) {
            throw AuthException.RefreshTokenNotFound()
        }
        validateRefreshToken(refreshToken)

        val user: User = userDomainService.getById(refreshToken.userId)
        val accessToken: String = userTokenService.generateAccessToken(user)
        val newRefreshToken: String = userTokenService.generateRefreshToken(user)

        return RefreshToken.Result(
            accessToken = accessToken,
            refreshToken = newRefreshToken,
        )
    }

    private fun validateRefreshToken(refreshToken: UserTokenClaims.RefreshToken) {
        if (userRefreshTokenRepository.existsByUserId(refreshToken.userId).not()) {
            throw AuthException.RefreshTokenNotFound()
        }
    }

}
