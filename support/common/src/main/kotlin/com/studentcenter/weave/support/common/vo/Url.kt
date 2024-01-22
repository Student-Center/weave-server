package com.studentcenter.weave.support.common.vo

/**
 * @property value URL 형식 문자열
 * @throws IllegalArgumentException URL 형식이 아닌 경우
 */
@JvmInline
value class Url(val value: String) {

    companion object {
        const val VALIDATION_REGEX = "^(http|https)://.*"
        const val VALIDATION_MESSAGE = "잘못된 URL 형식입니다."
    }

    init {
        require(value.matches(Regex(VALIDATION_REGEX))) { VALIDATION_MESSAGE }
    }
}
