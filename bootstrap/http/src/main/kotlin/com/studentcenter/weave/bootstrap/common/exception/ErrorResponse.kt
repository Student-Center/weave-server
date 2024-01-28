package com.studentcenter.weave.bootstrap.common.exception

import java.time.LocalDateTime

data class ErrorResponse(
    val exceptionCode: String,
    val message: String,
    val timeStamp: LocalDateTime = LocalDateTime.now(),
)
