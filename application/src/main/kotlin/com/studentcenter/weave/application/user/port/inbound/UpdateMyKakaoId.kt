package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.vo.KakaoId

fun interface UpdateMyKakaoId {

    fun invoke(kakaoId: KakaoId)

}
