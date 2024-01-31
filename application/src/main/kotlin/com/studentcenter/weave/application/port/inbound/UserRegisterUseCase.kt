package com.studentcenter.weave.application.port.inbound

import com.studentcenter.weave.domain.enum.Gender
import com.studentcenter.weave.domain.vo.Mbti
import com.studentcenter.weave.domain.enum.SocialLoginProvider
import com.studentcenter.weave.domain.vo.BirthYear
import com.studentcenter.weave.domain.vo.Nickname
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
