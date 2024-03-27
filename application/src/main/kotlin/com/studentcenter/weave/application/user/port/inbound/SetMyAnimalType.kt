package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.enums.AnimalType

fun interface SetMyAnimalType {

    fun invoke(animalType: AnimalType)

}
