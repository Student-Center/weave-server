package com.studentcenter.weave.support.common.functions

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DisposableHandle

fun Deferred<*>.invokeOnFailure(block: (Throwable) -> Unit): DisposableHandle {
    return invokeOnCompletion {
        if (it != null) {
            block(it)
        }
    }
}
