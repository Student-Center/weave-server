package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.vo.Mbti

fun interface ModifyMyMbti {

    fun invoke(mbti: Mbti)

}
