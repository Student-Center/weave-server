package com.studentcenter.weave.application.user.service.domain

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
import java.util.*


interface UserDomainService {

    fun getById(id: UUID): User

    fun findByKakaoId(kakaoId: KakaoId): User?

    fun create(
        nickname: Nickname,
        email: Email,
        gender: Gender,
        mbti: Mbti,
        birthYear: BirthYear,
        universityId: UUID,
        majorId: UUID,
    ): User

    fun deleteById(id: UUID)

    fun updateById(
        id: UUID,
        height: UpdateParam<Height?>? = null,
        animalType: UpdateParam<AnimalType?>? = null,
        mbti: Mbti? = null,
        kakaoId: UpdateParam<KakaoId?>? = null,
    ): User

    fun save(user: User)

    fun countAll(): Int

}
