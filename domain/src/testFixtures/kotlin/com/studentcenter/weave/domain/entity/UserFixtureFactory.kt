package com.studentcenter.weave.domain.entity

import com.studentcenter.weave.domain.enum.Gender
import com.studentcenter.weave.domain.enum.Mbti
import com.studentcenter.weave.domain.vo.BirthYear
import com.studentcenter.weave.domain.vo.MajorName
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.domain.vo.UniversityName
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url

object UserFixtureFactory {

    fun create(
        nickname: Nickname = Nickname("닉네임"),
        email: Email = Email("test@test.com"),
        gender: Gender = Gender.MAN,
        mbti: Mbti = Mbti.ENFJ,
        birthYear: BirthYear = BirthYear(1999),
        university: UniversityName = UniversityName("서울대학교"),
        major: MajorName = MajorName("컴퓨터 공학과"),
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
