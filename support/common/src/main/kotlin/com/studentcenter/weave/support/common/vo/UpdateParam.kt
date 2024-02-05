package com.studentcenter.weave.support.common.vo

data class UpdateParam<T>(
    val value: T?,
)

fun <T> UpdateParam<T>?.update(oldValue: T?, newValue: T?): T? {
    return if(this == null) oldValue else newValue
}
fun <T> T?.toUpdateParam() = UpdateParam(this)
