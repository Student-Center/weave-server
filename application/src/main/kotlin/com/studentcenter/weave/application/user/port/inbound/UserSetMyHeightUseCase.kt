package com.studentcenter.weave.application.user.port.inbound

import com.studentcenter.weave.domain.user.vo.Height

fun interface UserSetMyHeightUseCase {

    fun invoke(height: Height)

}
