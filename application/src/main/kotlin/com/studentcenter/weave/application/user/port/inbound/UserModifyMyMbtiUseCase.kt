package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.vo.Mbti

interface UserModifyMyMbtiUseCase {

    fun invoke(mbti: Mbti)

}
