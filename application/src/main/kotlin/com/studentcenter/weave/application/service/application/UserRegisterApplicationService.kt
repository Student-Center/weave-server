package com.studentcenter.weave.application.service.application

import com.studentcenter.weave.application.port.inbound.UserRegisterUseCase
import com.studentcenter.weave.application.service.domain.UserAuthInfoDomainService
import com.studentcenter.weave.application.service.domain.UserDomainService
import com.studentcenter.weave.application.service.util.UserTokenService
import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.entity.UserAuthInfo
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
        val user: User = User.create(
            nickname = command.nickname,
            email = command.email,
            gender = command.gender,
            birthYear = command.birthYear,
            mbti = command.mbti,
            universityId = command.universityId,
            majorId = command.majorId,
        ).also { userDomainService.save(it) }

        UserAuthInfo.create(
            user = user,
            socialLoginProvider = command.socialLoginProvider,
        ).let { userAuthInfoDomainService.save(it) }

        return UserRegisterUseCase.Result.Success(
            accessToken = userTokenService.generateAccessToken(user),
            refreshToken = userTokenService.generateRefreshToken(user),
        )
    }

}
