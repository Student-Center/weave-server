package com.studentcenter.weave.bootstrap.user.dto

import com.studentcenter.weave.domain.user.enums.AnimalType

data class UserSetMyAnimalTypeRequest (
    val animalType: AnimalType
)
