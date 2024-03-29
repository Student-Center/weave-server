package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.UniversityVerificationExceptionType
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.VerifyUniversityVerificationNumber
import com.studentcenter.weave.application.user.port.outbound.UserVerificationNumberRepository
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.application.user.service.domain.UserUniversityVerificationInfoDomainService
import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VerifyUniversityVerificationNumberService(
    private val userDomainService: UserDomainService,
    private val userSilDomainService: UserSilDomainService,
    private val userVerificationInfoDomainService: UserUniversityVerificationInfoDomainService,
    private val verificationNumberRepository: UserVerificationNumberRepository,
): VerifyUniversityVerificationNumber {

    @Transactional
    override fun invoke(command: VerifyUniversityVerificationNumber.Command) {
        val currentUserId = getCurrentUserAuthentication().userId
        if (userVerificationInfoDomainService.existsByUserId(currentUserId)) {
            throw CustomException(
                UniversityVerificationExceptionType.VERIFICATED_USER,
                "이미 인증된 유저입니다.",
            )
        }
        verificationNumberRepository.findByUserId(currentUserId)?.let {
            if (it.first != command.universityEmail || it.second != command.verificationNumber) {
                throw CustomException(
                    UniversityVerificationExceptionType.INVALID_VERIFICATION_INFORMATION,
                    "인증 정보가 일치하지 않습니다.",
                )
            }
        } ?: throw CustomException(
            UniversityVerificationExceptionType.VERIFICATION_INFORMATION_NOT_FOUND,
            "유저의 인증 요청을 찾을 수 없습니다.",
        )

        val user = userDomainService.getById(currentUserId)
        val verifiedUser = user.verifyUniversity().also { userDomainService.save(it) }
        UserUniversityVerificationInfo.create(verifiedUser, command.universityEmail).let {
            userVerificationInfoDomainService.save(it)
        }
        userSilDomainService.getByUserId(user.id).let {
            userSilDomainService.save(it.increment(getRewardAmount(command.universityEmail)))
        }
        verificationNumberRepository.deleteByUserId(user.id)
    }

    private fun getRewardAmount(verifiedEmail: Email): Long {
        var rewardAmount = DEFAULT_VERIFICATION_REWARD_AMOUNT

        if (EVENT_TARGET_EMAIL.contains(verifiedEmail.value)) {
            rewardAmount *= 2
        }

        return rewardAmount
    }

    companion object {
        const val DEFAULT_VERIFICATION_REWARD_AMOUNT = 30L
        val EVENT_TARGET_EMAIL = setOf(
            "60191667@mju.ac.kr"
        )
    }

}
