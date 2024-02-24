package com.studentcenter.weave.support.lock

import io.github.oshai.kotlinlogging.KotlinLogging
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

@Component
class DistributedLockAspect(
    innerRedissonClient: RedissonClient,
    innerDistributedLockTransactionProcessor: DistributedLockTransactionProcessor,
) {

    init {
        redissonClient = innerRedissonClient
        distributedLockTransactionProcessor = innerDistributedLockTransactionProcessor
    }

    companion object {

        val logger = KotlinLogging.logger { }

        lateinit var redissonClient: RedissonClient
            private set

        lateinit var distributedLockTransactionProcessor: DistributedLockTransactionProcessor
            private set

        const val REDISSON_LOCK_PREFIX = "LOCK:"
    }
}
