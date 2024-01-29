package com.studentcenter.weave.application.port.inbound

import com.studentcenter.weave.domain.enum.Gender
import com.studentcenter.weave.domain.enum.Mbti
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import com.studentcenter.weave.domain.vo.BirthYear
import com.studentcenter.weave.domain.vo.Major
import com.studentcenter.weave.domain.vo.Nickname
import com.studentcenter.weave.domain.vo.University
import com.studentcenter.weave.support.common.vo.Email

fun interface UserRegisterUseCase {

    fun invoke(command: Command): Result

    data class Command(
        val nickname: Nickname,
        val email: Email,
        val socialLoginProvider: SocialLoginProvider,
        val gender: Gender,
        val mbti: Mbti,
        val birthYear: BirthYear,
        val university: University,
        val major: Major
    )

    sealed class Result {

        data class Success(
            val accessToken: String,
            val refreshToken: String,
        ) : Result()

    }

}
