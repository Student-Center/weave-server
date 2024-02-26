package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.vo.KakaoId

interface UserSetMyKakaoIdUseCase {

    fun invoke(kakaoId: KakaoId)

}
