package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.AuthExceptionType
import com.studentcenter.weave.application.user.port.inbound.RefreshToken
import com.studentcenter.weave.application.user.port.outbound.UserRefreshTokenRepository
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.util.UserTokenService
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class RefreshTokenApplicationService(
    private val userDomainService: UserDomainService,
    private val userTokenService: UserTokenService,
    private val userRefreshTokenRepository: UserRefreshTokenRepository,
) : RefreshToken {

    @Transactional
    override fun invoke(command: RefreshToken.Command): RefreshToken.Result {
        val refreshToken: String = command.refreshToken
        val refreshTokenUserId: UUID = userTokenService
            .resolveRefreshToken(refreshToken)
            .userId

        val s = userRefreshTokenRepository.findByUserId(refreshTokenUserId)
        validateRefreshTokenExists(s)

        val user: User = userDomainService.getById(refreshTokenUserId)
        val accessToken: String = userTokenService.generateAccessToken(user)
        val newRefreshToken: String = userTokenService.generateRefreshToken(user)

        return RefreshToken.Result(
            accessToken = accessToken,
            refreshToken = newRefreshToken,
        )
    }

    private fun validateRefreshTokenExists(refreshToken: String?): String {
        if (refreshToken == null) {
            throw CustomException(
                type = AuthExceptionType.REFRESH_TOKEN_NOT_FOUND,
                message = "토큰 정보가 존재하지 않습니다."
            )
        }
        return refreshToken
    }

}
