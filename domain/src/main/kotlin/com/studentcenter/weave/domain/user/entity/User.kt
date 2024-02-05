package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.UpdateParam
import com.studentcenter.weave.support.common.vo.Url
import java.time.LocalDateTime
import java.util.*

data class User(
    val id: UUID = UuidCreator.create(),
    val nickname: Nickname,
    val email: Email,
    val gender: Gender,
    val mbti: Mbti,
    val birthYear: BirthYear,
    val universityId: UUID,
    val majorId: UUID,
    val avatar: Url? = null,
    val height: Height? = null,
    val animalType: AnimalType? = null,
    val registeredAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {

    fun update(
        height: UpdateParam<out Height>?,
        animalType: UpdateParam<out AnimalType>?,
        avatar: UpdateParam<out Url>?,
    ): User {
        return copy(
            height = if (height == null) this.height else height.value,
            animalType = if (animalType == null) this.animalType else animalType.value,
            avatar = if (avatar == null) this.avatar else avatar.value,
        )
    }

    companion object {

        fun create(
            nickname: Nickname,
            email: Email,
            gender: Gender,
            mbti: Mbti,
            birthYear: BirthYear,
            universityId: UUID,
            majorId: UUID,
            avatar: Url? = null,
        ): User {
            return User(
                nickname = nickname,
                email = email,
                gender = gender,
                mbti = mbti,
                birthYear = birthYear,
                universityId = universityId,
                majorId = majorId,
                avatar = avatar,
            )
        }
    }

}
