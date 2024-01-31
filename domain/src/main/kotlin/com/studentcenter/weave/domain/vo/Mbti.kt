package com.studentcenter.weave.domain.vo

@JvmInline
value class Mbti (
    val value: String
){
    companion object {
        const val VALIDATION_REGEX = "^(I|E|i|e)(S|N|s|n)(T|F|t|f)(J|P|j|p)"
    }

    init {
        require(value.matches(Regex(VALIDATION_REGEX))) {
            "MBTI는 I/E, S/N, T/F, J/P로 이루어진 4글자의 문자열이어야 합니다.(소문자 허용)"
        }
    }

}
