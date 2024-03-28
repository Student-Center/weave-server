package com.studentcenter.weave.application.user.vo

import com.studentcenter.weave.application.university.port.inbound.GetMajor
import com.studentcenter.weave.application.university.port.inbound.GetUniversity
import com.studentcenter.weave.domain.university.vo.MajorName
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

data class UserDetail(
    val id: UUID,
    val nickname: Nickname,
    val birthYear: BirthYear,
    val universityName: UniversityName,
    val majorName: MajorName,
    val avatar: Url?,
    val mbti: Mbti,
    val animalType: AnimalType?,
    val height: Height?,
    val isUnivVerified: Boolean,
) {

    companion object {

        fun of(
            user: User,
            getUniversity: GetUniversity,
            getMajor: GetMajor,
        ): UserDetail {
            return UserDetail(
                id = user.id,
                nickname = user.nickname,
                birthYear = user.birthYear,
                universityName = getUniversity.getById(user.universityId).name,
                majorName = getMajor.getById(user.majorId).name,
                avatar = user.avatar,
                mbti = user.mbti,
                animalType = user.animalType,
                height = user.height,
                isUnivVerified = user.isUnivVerified,
            )
        }
    }
}
