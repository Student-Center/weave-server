package com.studentcenter.weave.infrastructure.persistence.user.entity

import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.domain.user.vo.KakaoId
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.infrastructure.persistence.user.entity.UserProfileImageJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.support.common.vo.Email
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "`user`")
class UserJpaEntity(
    id: UUID,
    nickname: String,
    email: String,
    gender: Gender,
    mbti: String,
    birthYear: Int,
    universityId: UUID,
    majorId: UUID,
    profileImages: List<UserProfileImageJpaEntity> = emptyList(),
    height: Int? = null,
    animalType: AnimalType? = null,
    kakaoId: String? = null,
    isUnivVerified: Boolean = false,
    registeredAt: LocalDateTime,
    updatedAt: LocalDateTime,
) {

    @Id
    @Column(nullable = false, updatable = false)
    var id: UUID = id
        private set

    @Column(nullable = false, updatable = false)
    var nickname: String = nickname
        private set

    @Column(nullable = false, updatable = false)
    var email: String = email
        private set

    @Column(nullable = false, updatable = false, columnDefinition = "varchar(255)")
    @Enumerated(value = EnumType.STRING)
    var gender: Gender = gender
        private set

    @Column(nullable = false, columnDefinition = "varchar(255)")
    var mbti: String = mbti
        private set

    @Column(nullable = false)
    var birthYear: Int = birthYear
        private set

    @Column(nullable = true, updatable = true, columnDefinition = "integer")
    var height: Int? = height
        private set

    @Column(nullable = false, updatable = false)
    var universityId: UUID = universityId
        private set

    @Column(nullable = false, updatable = false)
    var majorId: UUID = majorId
        private set

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_profile_image",
        joinColumns = [JoinColumn(name = "user_id")]
    )
    var profileImages: List<UserProfileImageJpaEntity> = profileImages
        private set

    @Column(nullable = true, updatable = true, columnDefinition = "varchar(255)")
    @Enumerated(value = EnumType.STRING)
    var animalType: AnimalType? = animalType
        private set

    @Column(nullable = true, updatable = true)
    var kakaoId: String? = kakaoId
        private set

    @Column(nullable = false, updatable = true)
    var isUnivVerified: Boolean = isUnivVerified
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
                nickname = nickname.value,
                email = email.value,
                gender = gender,
                mbti = mbti.value,
                birthYear = birthYear.value,
                universityId = universityId,
                majorId = majorId,
                profileImages = profileImages.map { it.toJpaEntity() },
                height = height?.value,
                animalType = animalType,
                kakaoId = kakaoId?.value,
                isUnivVerified = isUnivVerified,
                registeredAt = registeredAt,
                updatedAt = updatedAt,
            )
        }
    }

    fun toDomain(): User {
        return User(
            id = id,
            nickname = Nickname(nickname),
            email = Email(email),
            gender = gender,
            mbti = Mbti(mbti),
            birthYear = BirthYear(birthYear),
            universityId = universityId,
            majorId = majorId,
            profileImages = profileImages.map { it.toDomain() },
            height = height?.let { Height(it) },
            animalType = animalType,
            kakaoId = kakaoId?.let { KakaoId(it) },
            isUnivVerified = isUnivVerified,
            registeredAt = registeredAt,
            updatedAt = updatedAt,
        )
    }

}
