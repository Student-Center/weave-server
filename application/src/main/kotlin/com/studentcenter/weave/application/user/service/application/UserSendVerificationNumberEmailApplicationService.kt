package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UserSendVerificationNumberEmailUseCase
import com.studentcenter.weave.application.user.port.outbound.UserVerificationNumberRepository
import com.studentcenter.weave.application.user.port.outbound.VerificationNumberMailer
import com.studentcenter.weave.application.user.service.util.VerificationNumberGenerator
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Service
import kotlin.time.Duration.Companion.minutes

@Service
class UserSendVerificationNumberEmailApplicationService(
    private val verificationNumberMailer: VerificationNumberMailer,
    private val userVerificationNumberRepository: UserVerificationNumberRepository,
): UserSendVerificationNumberEmailUseCase {

    override fun invoke(universityEmail: Email) {
        VerificationNumberGenerator.generate(VERIFICATION_NUMBER_SIZE).run {
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
        const val VERIFICATION_NUMBER_SIZE = 6
        const val DEFAULT_DURATION_MINUTE = 5
    }
}
