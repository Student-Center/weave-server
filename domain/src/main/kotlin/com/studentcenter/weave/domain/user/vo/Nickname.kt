package com.studentcenter.weave.domain.user.vo

@JvmInline
value class Nickname(
    val value: String
) {

    companion object {

        const val MAX_LENGTH = 10
    }

    init {
        require(value.length <= MAX_LENGTH) { "닉네임은 ${MAX_LENGTH}자 이하여야 합니다." }
    }
}
