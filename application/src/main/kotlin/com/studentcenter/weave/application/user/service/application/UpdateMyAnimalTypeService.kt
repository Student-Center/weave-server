package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UpdateMyAnimalType
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.application.user.service.domain.UserSilDomainService
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.support.common.vo.toUpdateParam
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateMyAnimalTypeService(
    private val userDomainService: UserDomainService,
    private val userSilDomainService: UserSilDomainService,
) : UpdateMyAnimalType {

    @Transactional
    override fun invoke(animalType: AnimalType) {
        val user: User = getCurrentUserAuthentication()
            .let { userDomainService.getById(it.userId) }

        userDomainService.updateById(
            id = user.id,
            animalType = animalType.toUpdateParam()
        )

        if (user.animalType == null) {
            userSilDomainService.incrementByUserId(
                userId = user.id,
                amount = 30
            )
        }
    }

}
