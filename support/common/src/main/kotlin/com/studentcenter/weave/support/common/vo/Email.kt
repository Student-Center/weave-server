package com.studentcenter.weave.support.common.vo

/**
 * @param value 이메일 주소 문자열
 * @throws IllegalArgumentException 이메일 주소가 올바른 형식이 아닌 경우
 */
@JvmInline
value class Email(val value: String) {

    companion object {
        const val VALIDATION_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"
        const val VALIDATION_MESSAGE = "잘못된 Email 형식 입니다."
    }

    init {
        require(value.matches(VALIDATION_REGEX.toRegex())) { VALIDATION_MESSAGE }
    }
}
