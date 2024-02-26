package com.studentcenter.weave.application.user.port.inbound

interface UserSetMyKakaoIdUseCase {

    fun invoke(kakaoId: String)
}
