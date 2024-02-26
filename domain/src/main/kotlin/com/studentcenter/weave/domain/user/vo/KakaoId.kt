package com.studentcenter.weave.domain.user.vo

@JvmInline
value class KakaoId(val value: String) {

    init {
        require(value.matches(Regex(VALIDATION_REGEX))) {
            "KakaoId는 영문, 숫자, 특수문자 빼기(-), 밑줄(_), 마침표(.)로 구성된 4자 이상 20자 이하의 문자열이어야 합니다."
        }
    }

    companion object {
        const val VALIDATION_REGEX = "^[a-zA-Z0-9-_.]{4,20}\$"
    }
}
