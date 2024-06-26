package com.studentcenter.weave.application.meetingTeam.vo

import com.studentcenter.weave.domain.meetingTeam.enums.MeetingMemberRole
import com.studentcenter.weave.domain.university.entity.Major
import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.university.vo.MajorName
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.entity.UserProfileImage
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.domain.user.vo.Mbti
import java.util.*

data class MeetingMemberDetail (
    val userId: UUID,
    val profileImages: List<UserProfileImage>,
    val universityName: String,
    val majorName: MajorName,
    val mbti: Mbti,
    val birthYear: BirthYear,
    val role: MeetingMemberRole,
    val animalType: AnimalType?,
    val height: Height?,
    val isUnivVerified: Boolean,
) {

    companion object {
        fun of(
            user: User,
            university: University,
            major: Major,
            role: MeetingMemberRole,
        ): MeetingMemberDetail {
            return MeetingMemberDetail(
                userId = user.id,
                profileImages = user.profileImages,
                universityName = university.displayName,
                majorName = major.name,
                mbti = user.mbti,
                birthYear = user.birthYear,
                role = role,
                animalType = user.animalType,
                height = user.height,
                isUnivVerified = user.isUnivVerified
            )
        }
    }

}
