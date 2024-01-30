package com.studentcenter.weave.application.service.domain

import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.entity.UserFixtureFactory
import com.studentcenter.weave.domain.enum.Gender
import com.studentcenter.weave.domain.enum.Mbti
import com.studentcenter.weave.domain.vo.BirthYear
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

class UserDomainServiceStub : UserDomainService {

    override fun getById(id: UUID): User {
        return UserFixtureFactory.create()
    }

    override fun create(
        nickname: Nickname,
        email: Email,
        gender: Gender,
        mbti: Mbti,
        birthYear: BirthYear,
        universityId: UUID,
        majorId: UUID,
        avatar: Url?
    ): User {
        TODO("Not yet implemented")
    }

}
