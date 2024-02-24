package com.studentcenter.weave.support.lock

import org.redisson.api.RLock
import java.util.concurrent.TimeUnit

/**
 * Distributed Lock
 * 적용 대상 함수는 별도의 트랜잭션으로 동작하며 커밋 이후 락을 해제한다.
 * @param key       락 식별자
 * @param timeUnit  시간 단위
 * @param waitTime  락을 획득하기 위해 대기할 시간
 * @param leaseTime 락을 획득한 후 락을 유지할 시간
 * @param function  적용 대상 함수
 * @return          함수 실행 결과
 */
fun <T> distributedLock(
    key: String,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    waitTime: Long = 5L,
    leaseTime: Long = 3L,
    function: () -> T,
): T {
    val rLock: RLock = (DistributedLockAspect.REDISSON_LOCK_PREFIX + key)
        .let { DistributedLockAspect.redissonClient.getLock(it) }

    try {
        val available: Boolean = rLock.tryLock(waitTime, leaseTime, timeUnit)
        check(available) {
            throw IllegalStateException("Lock is not available")
        }

        return DistributedLockAspect.distributedLockTransactionProcessor.proceed(function)
    } finally {
        try {
            rLock.unlock()
        } catch (e: IllegalMonitorStateException) {
            DistributedLockAspect.logger.info {
                "Redisson Lock Already UnLock Key : $key"
            }
        }
    }

}
