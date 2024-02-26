package com.studentcenter.weave.infrastructure.persistence.user.entity

import com.studentcenter.weave.domain.user.entity.UserKakaoAuth
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "user_kakao_auth")
class UserKakaoAuthJpaEntity(
    id: UUID,
    userId: UUID,
    kakaoId: String
) {

    @Id
    var id: UUID = id
        private set

    @Column(name = "user_id", nullable = false, unique = true)
    var userId: UUID = userId
        private set

    @Column(name = "kakao_id", nullable = false, unique = true)
    var kakaoId: String = kakaoId
        private set

    fun toDomain() = UserKakaoAuth(
        id = id,
        userId = userId,
        kakaoId = kakaoId
    )

    companion object {

        fun UserKakaoAuth.toJpaEntity() = UserKakaoAuthJpaEntity(
            id = id,
            userId = userId,
            kakaoId = kakaoId
        )

    }

}
