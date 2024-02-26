package com.studentcetner.weave.support.lock

import com.studentcenter.weave.support.lock.distributedLock
import io.mockk.every
import io.mockk.mockkStatic

object DistributedLockTestInitializer {


    fun mockExecutionByStatic() {
        mockkStatic("com.studentcenter.weave.support.lock.DistributedLockKt")
        every {
            distributedLock<Any?>(any(), any(), any(), captureLambda())
        } answers {
            val lambda: () -> Any? = arg<(() -> Any?)>(3)
            lambda()
        }
    }

}
