package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UserGetMyProfileUseCase
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.domain.university.vo.MajorName
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.domain.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserGetMyProfileApplicationService(
    private val userDomainService: UserDomainService,
    private val userSilDomainService: UserSilDomainService,
) : UserGetMyProfileUseCase {

    @Transactional(readOnly = true)
    override fun invoke(): UserGetMyProfileUseCase.Result {
        val user: User = getCurrentUserAuthentication()
            .let { userDomainService.getById(it.userId) }

        // TODO: Implement universityName
        val universityName = UniversityName("구현 예정")
        // TODO: Implement majorName
        val majorName = MajorName("구현 예정")
        // TODO: Implement isUniversityEmailVerified
        val isUniversityEmailVerified = false

        val silAmount: Long = userSilDomainService
            .getByUserId(user.id)
            .amount

        return UserGetMyProfileUseCase.Result(
            id = user.id,
            nickname = user.nickname,
            birthYear = user.birthYear,
            universityName = universityName,
            majorName = majorName,
            avatar = user.avatar,
            mbti = user.mbti,
            animalType = user.animalType,
            height = user.height,
            isUniversityEmailVerified = isUniversityEmailVerified,
            sil = silAmount,
        )
    }

}
