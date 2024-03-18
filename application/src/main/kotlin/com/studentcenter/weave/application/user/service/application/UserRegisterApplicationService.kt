package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.user.port.inbound.UserRegisterUseCase
import com.studentcenter.weave.application.user.port.outbound.UserEventPort
import com.studentcenter.weave.application.user.service.domain.UserAuthInfoDomainService
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.application.user.service.util.UserTokenService
import com.studentcenter.weave.domain.user.entity.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRegisterApplicationService(
    private val userTokenService: UserTokenService,
    private val userDomainService: UserDomainService,
    private val userSilDomainService: UserSilDomainService,
    private val userAuthInfoDomainService: UserAuthInfoDomainService,
    private val userEventPort: UserEventPort,
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
        userSilDomainService.create(user.id)

        runBlocking {
            launch {
                sendRegistrationMessage(user)
            }
        }

        return UserRegisterUseCase.Result.Success(
            accessToken = userTokenService.generateAccessToken(user),
            refreshToken = userTokenService.generateRefreshToken(user),
        )
    }

    fun sendRegistrationMessage(user: User) {
        userEventPort.sendRegistrationMessage(user)
    }

}
