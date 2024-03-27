package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.user.port.inbound.RegisterUser
import com.studentcenter.weave.application.user.port.outbound.UserEventPort
import com.studentcenter.weave.application.user.service.domain.UserAuthInfoDomainService
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.application.user.service.util.UserTokenService
import com.studentcenter.weave.domain.user.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterApplicationService(
    private val userTokenService: UserTokenService,
    private val userDomainService: UserDomainService,
    private val userSilDomainService: UserSilDomainService,
    private val userAuthInfoDomainService: UserAuthInfoDomainService,
    private val userEventPort: UserEventPort,
) : RegisterUser {

    @Transactional
    override fun invoke(command: RegisterUser.Command): RegisterUser.Result {
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

        val result = RegisterUser.Result.Success(
            accessToken = userTokenService.generateAccessToken(user),
            refreshToken = userTokenService.generateRefreshToken(user),
        )

        val userCount = userDomainService.countAll()

        CoroutineScope(Dispatchers.IO).launch {
            userEventPort.sendRegistrationMessage(
                user = user,
                userCount = userCount,
            )
        }

        return result
    }

}
