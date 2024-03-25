package com.studentcenter.weave.bootstrap.user.dto

import com.studentcenter.weave.domain.user.enums.AnimalType

data class UserGetAvailableAnimalTypesResponse(
    val items: List<Item>
) {

    data class Item(
        val name: String,
        val description: String,
    )

    companion object {

        fun create(): UserGetAvailableAnimalTypesResponse {
            return AnimalType.entries.map {
                Item(
                    name = it.name,
                    description = it.description
                )
            }.let {
                UserGetAvailableAnimalTypesResponse(it)
            }
        }

    }

}
