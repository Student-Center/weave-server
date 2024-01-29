package com.studentcenter.weave.domain.entity

import com.studentcenter.weave.domain.enum.Gender
import com.studentcenter.weave.domain.enum.Mbti
import com.studentcenter.weave.domain.vo.BirthYear
import com.studentcenter.weave.domain.vo.MajorName
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.domain.vo.UniversityName
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
    val university: UniversityName,
    val major: MajorName,
    val avatar: Url? = null,
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
            university: UniversityName,
            major: MajorName,
            avatar: Url? = null,
        ): User {
            return User(
                nickname = nickname,
                email = email,
                gender = gender,
                mbti = mbti,
                birthYear = birthYear,
                university = university,
                major = major,
                avatar = avatar,
            )
        }
    }

}
