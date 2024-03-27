package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.university.service.domain.MajorDomainService
import com.studentcenter.weave.application.university.service.domain.UniversityDomainService
import com.studentcenter.weave.application.user.port.inbound.GetMyProfile
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetMyProfileApplicationService(
    private val userDomainService: UserDomainService,
    private val userSilDomainService: UserSilDomainService,
    private val universityDomainService: UniversityDomainService,
    private val majorDomainService: MajorDomainService,
) : GetMyProfile {

    @Transactional(readOnly = true)
    override fun invoke(): GetMyProfile.Result {
        val user: User = getCurrentUserAuthentication()
            .let { userDomainService.getById(it.userId) }

        val universityName = universityDomainService
            .getById(user.universityId)
            .name
        val majorName = majorDomainService
            .getById(user.majorId)
            .name
        val silAmount: Long = userSilDomainService
            .getByUserId(user.id)
            .amount

        return GetMyProfile.Result(
            id = user.id,
            nickname = user.nickname,
            birthYear = user.birthYear,
            universityName = universityName,
            majorName = majorName,
            avatar = user.avatar,
            mbti = user.mbti,
            animalType = user.animalType,
            height = user.height,
            kakaoId = user.kakaoId,
            isUniversityEmailVerified = user.isUnivVerified,
            sil = silAmount,
        )
    }

}
