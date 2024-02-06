package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.enums.AnimalType

interface UserSetMyAnimalTypeUseCase {

    fun invoke(animalType: AnimalType)

}
