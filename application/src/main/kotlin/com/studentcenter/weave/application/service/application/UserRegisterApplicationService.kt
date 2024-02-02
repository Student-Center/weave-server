package com.studentcenter.weave.application.service.application

import com.studentcenter.weave.application.port.inbound.UserRegisterUseCase
import com.studentcenter.weave.application.service.domain.UserAuthInfoDomainService
import com.studentcenter.weave.application.service.domain.UserDomainService
import com.studentcenter.weave.application.service.util.UserTokenService
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRegisterApplicationService(
    private val userTokenService: UserTokenService,
    private val userDomainService: UserDomainService,
    private val userAuthInfoDomainService: UserAuthInfoDomainService,
) : UserRegisterUseCase {

    @Transactional
    override fun invoke(command: UserRegisterUseCase.Command): UserRegisterUseCase.Result {
        val user: User = userDomainService.create(
            nickname = command.nickname,
            email = command.email,
            gender = command.gender,
            mbti = command.mbti,
            birthYear = command.birthYear,
            universityId = command.universityId,
            majorId = command.majorId,
        )

        userAuthInfoDomainService.create(
            user = user,
            socialLoginProvider = command.socialLoginProvider,
        )

        return UserRegisterUseCase.Result.Success(
            accessToken = userTokenService.generateAccessToken(user),
            refreshToken = userTokenService.generateRefreshToken(user),
        )
    }

}
