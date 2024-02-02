package com.studentcenter.weave.application.port.inbound

import com.studentcenter.weave.domain.user.enums.Gender
import com.studentcenter.weave.domain.user.vo.Mbti
import com.studentcenter.weave.domain.user.enums.SocialLoginProvider
import com.studentcenter.weave.domain.user.vo.BirthYear
import com.studentcenter.weave.domain.user.vo.Nickname
import com.studentcenter.weave.support.common.vo.Email
import java.util.*

fun interface UserRegisterUseCase {

    fun invoke(command: Command): Result

    data class Command(
        val nickname: Nickname,
        val email: Email,
        val socialLoginProvider: SocialLoginProvider,
        val gender: Gender,
        val mbti: Mbti,
        val birthYear: BirthYear,
        val universityId: UUID,
        val majorId: UUID,
    )

    sealed class Result {

        data class Success(
            val accessToken: String,
            val refreshToken: String,
        ) : Result()

    }

}
