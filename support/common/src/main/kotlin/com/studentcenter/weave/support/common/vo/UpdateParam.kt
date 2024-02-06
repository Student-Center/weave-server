package com.studentcenter.weave.support.common.vo

data class UpdateParam<T>(
    val value: T?,
)

fun <T> UpdateParam<T>?.getUpdateValue(oldValue: T?): T? {
    return if(this == null) oldValue else this.value
}
fun <T> T?.toUpdateParam() = UpdateParam(this)
