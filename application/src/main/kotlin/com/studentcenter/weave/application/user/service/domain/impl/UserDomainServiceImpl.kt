package com.studentcenter.weave.application.user.service.domain.impl

import com.studentcenter.weave.application.user.port.outbound.UserRepository
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDomainServiceImpl(
    private val userRepository: UserRepository
) : UserDomainService {

    override fun getById(id: UUID): User {
        return userRepository.getById(id)
    }

    override fun create(
        nickname: Nickname,
        email: Email,
        gender: Gender,
        mbti: Mbti,
        birthYear: BirthYear,
        universityId: UUID,
        majorId: UUID,
        avatar: Url?,
    ): User {
        return User.create(
            nickname = nickname,
            email = email,
            gender = gender,
            mbti = mbti,
            birthYear = birthYear,
            universityId = universityId,
            majorId = majorId,
            avatar = avatar,
        ).also { userRepository.save(it) }
    }
}
