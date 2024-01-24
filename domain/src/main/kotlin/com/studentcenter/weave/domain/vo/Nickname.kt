package com.studentcenter.weave.domain.vo

@JvmInline
value class Nickname(
    val value: String
) {
    init {
        require(value.length in 3..10) { "닉네임은 3글자 이상 10자 이하여야 합니다." }
    }
}
