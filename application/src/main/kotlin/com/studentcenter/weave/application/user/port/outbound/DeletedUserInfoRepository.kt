package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.DeletedUserInfo

interface DeletedUserInfoRepository {

    fun save(deletedUserInfo: DeletedUserInfo)

}
