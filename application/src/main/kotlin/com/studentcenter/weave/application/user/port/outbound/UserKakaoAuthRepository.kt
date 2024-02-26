package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.UserKakaoAuth
import java.util.UUID

interface UserKakaoAuthRepository {

    fun findByUserId(userId: UUID): UserKakaoAuth?

    fun findByKakaoId(kakaoId: String): UserKakaoAuth?

    fun save(userKakaoAuth: UserKakaoAuth)

}
