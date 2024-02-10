package com.studentcenter.weave.support.common.dto

import java.util.UUID

data class ScrollRequest (
    val lastItemId: UUID?,
    val limit: Int
)
