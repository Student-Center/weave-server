package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.exception.UniversityVerificationException
import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.VerifyUniversityVerificationNumber
import com.studentcenter.weave.application.user.port.outbound.UserRepository
import com.studentcenter.weave.application.user.port.outbound.UserVerificationNumberRepository
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.application.user.service.domain.UserUniversityVerificationInfoDomainService
import com.studentcenter.weave.domain.user.entity.UserUniversityVerificationInfo
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VerifyUniversityVerificationNumberService(
    private val userSilDomainService: UserSilDomainService,
    private val userVerificationInfoDomainService: UserUniversityVerificationInfoDomainService,
    private val verificationNumberRepository: UserVerificationNumberRepository,
    private val userRepository: UserRepository,
) : VerifyUniversityVerificationNumber {

    @Transactional
    override fun invoke(command: VerifyUniversityVerificationNumber.Command) {
        val currentUserId = getCurrentUserAuthentication().userId
        if (userVerificationInfoDomainService.existsByUserId(currentUserId)) {
            throw UniversityVerificationException.AlreadyVerifiedUser()
        }
        verificationNumberRepository.findByUserId(currentUserId)?.let {
            if (it.first != command.universityEmail || it.second != command.verificationNumber) {
                throw UniversityVerificationException.InvalidVerificationInformation()
            }
        } ?: throw UniversityVerificationException.VerificationInformationNotFound()

        val user = userRepository.getById(currentUserId)
        val verifiedUser = user.verifyUniversity().also { userRepository.save(it) }
        UserUniversityVerificationInfo.create(verifiedUser, command.universityEmail).let {
            userVerificationInfoDomainService.save(it)
        }

        userSilDomainService
            .getByUserId(user.id)
            .increment(getRewardAmount(command.universityEmail))
            .let { userSilDomainService.save(it) }

        verificationNumberRepository.deleteByUserId(user.id)
    }

    private fun getRewardAmount(verifiedEmail: Email): Long {
        var rewardAmount = DEFAULT_VERIFICATION_REWARD_AMOUNT

        if (userRepository.isPreRegisteredEmail(verifiedEmail)) {
            rewardAmount *= 2
        }

        return rewardAmount
    }

    companion object {

        const val DEFAULT_VERIFICATION_REWARD_AMOUNT = 30L
    }

}
