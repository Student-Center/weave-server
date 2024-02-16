package com.studentcenter.weave.support.common.dto

abstract class ScrollResponse<T, N>(
    open val items: List<T>,
    open val next: N,
    open val total: Int,
)
