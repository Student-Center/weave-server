package com.studentcenter.weave.support.common.dto

abstract class ScrollRequest<N>(
    open val next: N,
    open val limit: Int
)
