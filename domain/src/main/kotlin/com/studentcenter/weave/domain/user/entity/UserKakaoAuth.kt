package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.time.LocalDateTime
import java.util.*

data class UserKakaoAuth(
    val id: UUID = UuidCreator.create(),
    val userId: UUID,
    val kakaoId: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {

    companion object {

        fun create(
            userId: UUID,
            kakaoId: String
        ) = UserKakaoAuth(
            userId = userId,
            kakaoId = kakaoId
        )

    }

}
