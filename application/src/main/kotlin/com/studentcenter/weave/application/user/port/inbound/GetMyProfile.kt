package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.university.vo.MajorName
import com.studentcenter.weave.domain.university.vo.UniversityName
import com.studentcenter.weave.domain.user.enums.AnimalType
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Height
import com.studentcenter.weave.domain.user.vo.KakaoId
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.vo.Url
import java.util.*

fun interface GetMyProfile {

    fun invoke(): Result

    data class Result(
        val id: UUID,
        val nickname: Nickname,
        val birthYear: BirthYear,
        val universityName: UniversityName,
        val majorName: MajorName,
        val avatar: Url?,
        val mbti: Mbti,
        val animalType: AnimalType?,
        val height: Height?,
        val kakaoId: KakaoId?,
        val isUniversityEmailVerified: Boolean,
        val sil: Long,
    )

}
