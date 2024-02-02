package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

object UserFixtureFactory {

    fun create(
        nickname: Nickname = Nickname("닉네임"),
        email: Email = Email("test@test.com"),
        gender: Gender = Gender.MAN,
        mbti: Mbti = Mbti("EnTJ"),
        birthYear: BirthYear = BirthYear(1999),
        universityId: UUID = UuidCreator.create(),
        majorId: UUID = UuidCreator.create(),
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
