package com.studentcenter.weave.application.service.domain

import com.studentcenter.weave.domain.entity.User
import java.util.UUID


interface UserDomainService {

    fun getById(id: UUID): User

}
