package com.studentcenter.weave.domain.user.entity

import com.studentcenter.weave.domain.common.AggregateRoot
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.domain.user.vo.KakaoId
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.uuid.UuidCreator
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.common.vo.UpdateParam
import com.studentcenter.weave.support.common.vo.Url
import com.studentcenter.weave.support.common.vo.getUpdateValue
import java.time.LocalDateTime
import java.util.*

data class User(
    override val id: UUID = UuidCreator.create(),
    val nickname: Nickname,
    val email: Email,
    val gender: Gender,
    val mbti: Mbti,
    val birthYear: BirthYear,
    val universityId: UUID,
    val majorId: UUID,
    val profileImages: List<UserProfileImage> = emptyList(),
    val height: Height? = null,
    val animalType: AnimalType? = null,
    val kakaoId: KakaoId? = null,
    val isUnivVerified: Boolean = false,
    val registeredAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) : AggregateRoot {

    val avatar: Url?
        get() = profileImages.firstOrNull()?.imageUrl

    fun updateProfileImage(
        imageId: UUID,
        extension: UserProfileImage.Extension,
        getProfileImageSourceAction: (UUID, UserProfileImage.Extension) -> Url,
    ): User {
        val image = UserProfileImage.create(imageId, extension, getProfileImageSourceAction)

        return copy(
            profileImages = listOf(image),
            updatedAt = LocalDateTime.now(),
        )
    }

    fun update(
        height: UpdateParam<Height?>? = null,
        animalType: UpdateParam<AnimalType?>? = null,
        mbti: Mbti? = null,
        kakaoId: UpdateParam<KakaoId?>? = null,
    ): User {
        return copy(
            height = height.getUpdateValue(this.height),
            animalType = animalType.getUpdateValue(this.animalType),
            kakaoId = kakaoId.getUpdateValue(this.kakaoId),
            mbti = mbti ?: this.mbti,
            updatedAt = LocalDateTime.now(),
        )
    }

    fun verifyUniversity(): User {
        return copy(
            isUnivVerified = true
        )
    }

    companion object {

        fun create(
            nickname: Nickname,
            email: Email,
            gender: Gender,
            mbti: Mbti,
            birthYear: BirthYear,
            universityId: UUID,
            majorId: UUID,
        ): User {
            return User(
                nickname = nickname,
                email = email,
                gender = gender,
                mbti = mbti,
                birthYear = birthYear,
                universityId = universityId,
                majorId = majorId,
            )
        }
    }

}
