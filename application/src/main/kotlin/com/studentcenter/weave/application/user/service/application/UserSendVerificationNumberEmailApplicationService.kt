package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.UniversityVerificationExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UserSendVerificationNumberEmailUseCase
import com.studentcenter.weave.application.user.port.outbound.UserVerificationNumberRepository
import com.studentcenter.weave.application.user.port.outbound.VerificationNumberMailer
import com.studentcenter.weave.application.user.service.domain.UserUniversityVerificationInfoDomainService
import com.studentcenter.weave.application.user.vo.UserUniversityVerificationNumber
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Service
import kotlin.time.Duration.Companion.minutes

@Service
class UserSendVerificationNumberEmailApplicationService(
    private val verificationNumberMailer: VerificationNumberMailer,
    private val userVerificationNumberRepository: UserVerificationNumberRepository,
    private val verificationInfoDomainService: UserUniversityVerificationInfoDomainService,
) : UserSendVerificationNumberEmailUseCase {

    override fun invoke(universityEmail: Email) {
        if (verificationInfoDomainService.existsByEmail(universityEmail)) {
            throw CustomException(
                type = UniversityVerificationExceptionType.VERIFICATED_EMAIL,
                message = "이미 인증된 이메일입니다."
            )
        }

        UserUniversityVerificationNumber.generate().run {
            userVerificationNumberRepository.save(
                getCurrentUserAuthentication().userId,
                universityEmail,
                this,
                DEFAULT_DURATION_MINUTE.minutes.inWholeSeconds
            )
            verificationNumberMailer.send(universityEmail, this, DEFAULT_DURATION_MINUTE.minutes)
        }
    }

    companion object {
        const val DEFAULT_DURATION_MINUTE = 5
    }
}
