package com.studentcenter.weave.support.common.vo

data class UpdateParam<T>(
    val value: T?,
)

fun <T> T?.toUpdateParam() = UpdateParam(this)
