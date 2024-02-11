package com.studentcenter.weave.infrastructure.redis.user.repository

import com.studentcenter.weave.infrastructure.redis.user.entity.UserVerificationNumberRedisHash
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserVerificationNumberRedisRepository : CrudRepository<UserVerificationNumberRedisHash, UUID>
