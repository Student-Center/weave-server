package com.studentcenter.weave.support.common.dto

import java.util.UUID

abstract class ScrollResponse<T>(
    open val item: List<T>,
    open val lastItemId: UUID?,
    open val limit: Int,
)
