package com.studentcenter.weave.application.user.service.application

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.user.port.inbound.UserSetMyAnimalTypeUseCase
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.support.common.vo.toUpdateParam
import org.springframework.stereotype.Service

@Service
class UserSetMyAnimalTypeApplicationService(
    private val userDomainService: UserDomainService,
) : UserSetMyAnimalTypeUseCase {

    override fun invoke(animalType: AnimalType) {
        getCurrentUserAuthentication()
            .let {
                userDomainService.updateById(
                    id = it.userId,
                    animalType = animalType.toUpdateParam()
                )
            }
    }

}
