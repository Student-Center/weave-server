package com.studentcenter.weave.support.common.exception

class CustomException(
    val type: CustomExceptionType,
    override val message: String
) : RuntimeException(message)
