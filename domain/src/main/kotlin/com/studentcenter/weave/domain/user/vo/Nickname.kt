package com.studentcenter.weave.domain.user.vo

@JvmInline
value class Nickname(
    val value: String
) {

    companion object {

        const val MIN_LENGTH = 1
        const val MAX_LENGTH = 10
    }

    init {
        require(value.length in MIN_LENGTH..MAX_LENGTH) { "닉네임은 ${MIN_LENGTH}글자 이상 ${MAX_LENGTH}자 이하여야 합니다." }
    }
}
