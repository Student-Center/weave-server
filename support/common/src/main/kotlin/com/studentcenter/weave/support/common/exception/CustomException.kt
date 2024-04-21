package com.studentcenter.weave.support.common.exception

abstract class CustomException(
    codePrefix: String = DEFAULT_CODE_PREFIX,
    codeNumber: Int,
    override val message: String = DEFAULT_MESSAGE,
) : RuntimeException(message) {

    val code: String = "$codePrefix-${
        codeNumber.toString().padStart(DEFAULT_CODE_NUMBER_LENGTH, DEFAULT_CODE_NUMBER_PAD_CHAR)
    }"

    companion object {

        const val DEFAULT_CODE_NUMBER_LENGTH = 3
        const val DEFAULT_CODE_NUMBER_PAD_CHAR = '0'
        const val DEFAULT_CODE_PREFIX = "UNKNOWN"
        const val DEFAULT_MESSAGE = "알 수 없는 오류가 발생했습니다."

    }

}
