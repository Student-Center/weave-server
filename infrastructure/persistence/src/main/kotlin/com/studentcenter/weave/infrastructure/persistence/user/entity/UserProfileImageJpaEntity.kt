package com.studentcenter.weave.infrastructure.persistence.user.entity

import com.studentcenter.weave.domain.user.entity.UserProfileImage
import com.studentcenter.weave.support.common.vo.Url
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.util.*

@Embeddable
@Table(name = "user_profile_image")
class UserProfileImageJpaEntity(
    id: UUID,
    extension: UserProfileImage.Extension,
    imageUrl: String,
) {

    @Column(nullable = false, updatable = false)
    var id: UUID = id
        private set

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, columnDefinition = "varchar(255)")
    var extension: UserProfileImage.Extension = extension
        private set

    @Column(nullable = false, updatable = false)
    var imageUrl: String = imageUrl
        private set

    fun toDomain(): UserProfileImage {
        return UserProfileImage(
            id = id,
            extension = extension,
            imageUrl = Url(imageUrl),
        )
    }

    companion object {

        fun UserProfileImage.toJpaEntity(): UserProfileImageJpaEntity {
            return UserProfileImageJpaEntity(
                id = id,
                extension = extension,
                imageUrl = imageUrl.value,
            )
        }
    }

}
