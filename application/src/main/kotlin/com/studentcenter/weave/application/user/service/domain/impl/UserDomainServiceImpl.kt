package com.studentcenter.weave.application.user.service.domain.impl

import com.studentcenter.weave.application.user.port.outbound.UserRepository
import com.studentcenter.weave.application.user.service.domain.UserDomainService
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.domain.user.vo.KakaoId
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.UpdateParam
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDomainServiceImpl(
    private val userRepository: UserRepository,
) : UserDomainService {

    override fun getById(id: UUID): User {
        return userRepository.getById(id)
    }

    override fun findByKakaoId(kakaoId: KakaoId): User? {
        return userRepository.findByKakaoId(kakaoId)
    }

    override fun create(
        nickname: Nickname,
        email: Email,
        gender: Gender,
        mbti: Mbti,
        birthYear: BirthYear,
        universityId: UUID,
        majorId: UUID,
    ): User {
        return User.create(
            nickname = nickname,
            email = email,
            gender = gender,
            mbti = mbti,
            birthYear = birthYear,
            universityId = universityId,
            majorId = majorId,
        ).also { userRepository.save(it) }
    }

    override fun deleteById(id: UUID) {
        userRepository.deleteById(id)
    }

    override fun updateById(
        id: UUID,
        height: UpdateParam<Height?>?,
        animalType: UpdateParam<AnimalType?>?,
        mbti: Mbti?,
        kakaoId: UpdateParam<KakaoId?>?,
    ): User {
        return userRepository
            .getById(id)
            .update(height, animalType, mbti, kakaoId)
            .also { userRepository.save(it) }
    }

    override fun save(user: User) {
        userRepository.save(user)
    }

}
