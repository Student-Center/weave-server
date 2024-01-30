package com.studentcenter.weave.domain.entity

import com.studentcenter.weave.domain.enum.Gender
import com.studentcenter.weave.domain.enum.Mbti
import com.studentcenter.weave.domain.vo.BirthYear
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

object UserFixtureFactory {

    fun create(
        nickname: Nickname = Nickname("닉네임"),
        email: Email = Email("test@test.com"),
        gender: Gender = Gender.MAN,
        mbti: Mbti = Mbti.ENFJ,
        birthYear: BirthYear = BirthYear(1999),
        universityId: UUID = UUID.randomUUID(),
        majorId: UUID = UUID.randomUUID(),
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
