package com.studentcenter.weave.domain.entity

import com.studentcenter.weave.domain.enum.AnimalType
import com.studentcenter.weave.domain.enum.Gender
import com.studentcenter.weave.domain.vo.Mbti
import com.studentcenter.weave.domain.vo.BirthYear
import com.studentcenter.weave.domain.vo.Height
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Email
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
