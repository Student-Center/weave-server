package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.UniversityVerificationExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.SendVerificationEmail
import com.studentcenter.weave.application.user.port.outbound.UserVerificationNumberRepository
import com.studentcenter.weave.application.user.port.outbound.VerificationNumberMailer
import com.studentcenter.weave.application.user.service.domain.UserUniversityVerificationInfoDomainService
import com.studentcenter.weave.application.user.vo.UserUniversityVerificationNumber
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Service
import java.util.*
import kotlin.time.Duration.Companion.minutes

@Service
class SendVerificationEmailService(
    private val verificationNumberMailer: VerificationNumberMailer,
    private val userVerificationNumberRepository: UserVerificationNumberRepository,
    private val verificationInfoDomainService: UserUniversityVerificationInfoDomainService,
): SendVerificationEmail {

    override fun invoke(universityEmail: Email) {
        checkAlreadyVerifiedUniversityEmail(universityEmail)
        val verificationNumber = getOrGenerateVerificationNumber(
            getCurrentUserAuthentication().userId,
            universityEmail,
        )

        verificationNumberMailer.send(universityEmail, verificationNumber, DEFAULT_DURATION_MINUTE.minutes)
    }

    private fun checkAlreadyVerifiedUniversityEmail(universityEmail: Email) {
        if (verificationInfoDomainService.existsByEmail(universityEmail)) {
            throw CustomException(
                type = UniversityVerificationExceptionType.VERIFICATED_EMAIL,
                message = "이미 인증된 이메일입니다."
            )
        }
    }

    private fun getOrGenerateVerificationNumber(userId: UUID, universityEmail: Email): UserUniversityVerificationNumber {
        return userVerificationNumberRepository.findByUserId(userId)?.let {
            if (universityEmail == it.first) it.second else null
        } ?: generateVerificationNumber(userId, universityEmail)
    }

    private fun generateVerificationNumber(userId: UUID, universityEmail: Email): UserUniversityVerificationNumber {
        return UserUniversityVerificationNumber.generate().also {
            userVerificationNumberRepository.save(
                userId,
                universityEmail,
                it,
                DEFAULT_DURATION_MINUTE.minutes.inWholeSeconds
            )
        }
    }

    companion object {
        const val DEFAULT_DURATION_MINUTE = 5
    }
}
