package com.studentcenter.weave.infrastructure.persistence.entity

import com.studentcenter.weave.domain.entity.User
import com.studentcenter.weave.domain.enum.Gender
import com.studentcenter.weave.domain.enum.Mbti
import com.studentcenter.weave.domain.vo.BirthYear
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.Url
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "`user`")
class UserJpaEntity(
    id: UUID,
    nickname: Nickname,
    email: Email,
    gender: Gender,
    mbti: Mbti,
    birthYear: BirthYear,
    universityId: UUID,
    majorId: UUID,
    avatar: Url? = null,
    registeredAt: LocalDateTime,
    updatedAt: LocalDateTime,
) {

    @Id
    @Column(nullable = false, updatable = false)
    var id: UUID = id
        private set

    @Column(nullable = false, updatable = false)
    var nickname: Nickname = nickname
        private set

    @Column(nullable = false, updatable = false)
    var email: Email = email
        private set

    @Column(nullable = false, updatable = false, columnDefinition = "varchar(255)")
    @Enumerated(value = EnumType.STRING)
    var gender: Gender = gender
        private set

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(value = EnumType.STRING)
    var mbti: Mbti = mbti
        private set

    @Column(nullable = false)
    var birthYear: BirthYear = birthYear
        private set

    @Column(nullable = false, updatable = false)
    var universityId: UUID = universityId
        private set

    @Column(nullable = false, updatable = false)
    var majorId: UUID = majorId
        private set

    @Column()
    var avatar: Url? = avatar
        private set

    @Column(nullable = false, updatable = false)
    var registeredAt: LocalDateTime = registeredAt
        private set

    @Column(nullable = false)
    var updatedAt: LocalDateTime = updatedAt
        private set

    companion object {
        fun User.toJpaEntity(): UserJpaEntity {
            return UserJpaEntity(
                id = id,
                nickname = nickname,
                email = email,
                gender = gender,
                mbti = mbti,
                birthYear = birthYear,
                universityId = universityId,
                majorId = majorId,
                avatar = avatar,
                registeredAt = registeredAt,
                updatedAt = updatedAt,
            )
        }
    }

}
