package com.studentcenter.weave.support.lock

import org.redisson.api.RLock
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Distributed Lock
 * 적용 대상 함수는 별도의 트랜잭션으로 동작하며 커밋 이후 락을 해제한다.
 * @param key       락 식별자
 * @param waitDuration  락 대기 시간
 * @param leaseDuration 락 유지 시간
 * @param function  적용 대상 함수
 * @return          함수 실행 결과
 */
fun <T> distributedLock(
    key: String,
    waitDuration: Duration = 5.seconds,
    leaseDuration: Duration = 3.seconds,
    function: () -> T,
): T {
    val rLock: RLock = (DistributedLockAspect.REDISSON_LOCK_PREFIX + key)
        .let { DistributedLockAspect.redissonClient.getLock(it) }

    try {
        val available: Boolean = rLock.tryLock(
            waitDuration.inWholeSeconds,
            leaseDuration.inWholeSeconds,
            TimeUnit.SECONDS,
        )
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
