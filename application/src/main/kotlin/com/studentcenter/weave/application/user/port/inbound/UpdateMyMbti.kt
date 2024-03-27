package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.vo.Mbti

fun interface UpdateMyMbti {

    fun invoke(mbti: Mbti)

}
