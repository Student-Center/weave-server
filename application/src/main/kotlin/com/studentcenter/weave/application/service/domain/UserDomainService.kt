package com.studentcenter.weave.application.service.domain

import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.enum.Gender
import com.studentcenter.weave.domain.enum.Mbti
import com.studentcenter.weave.domain.vo.BirthYear
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url
import java.util.*


interface UserDomainService {

    fun getById(id: UUID): User

    fun create(
        nickname: Nickname,
        email: Email,
        gender: Gender,
        mbti: Mbti,
        birthYear: BirthYear,
        universityId: UUID,
        majorId: UUID,
        avatar: Url? = null,
    ): User

}
