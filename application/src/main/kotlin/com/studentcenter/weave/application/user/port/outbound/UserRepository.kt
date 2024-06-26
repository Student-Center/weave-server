package com.studentcenter.weave.application.user.port.outbound

import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.domain.user.vo.KakaoId
import com.studentcenter.weave.support.common.vo.Email
import java.util.*

interface UserRepository {

    fun save(user: User)

    fun getById(id: UUID): User

    fun getAllByIds(ids: List<UUID>): List<User>

    fun findByKakaoId(kakaoId: KakaoId): User?

    fun deleteById(id: UUID)

    fun countAll(): Int

    fun isPreRegisteredEmail(email: Email): Boolean

}
