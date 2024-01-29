package com.studentcenter.weave.domain.vo

@JvmInline
value class UniversityName(val value: String) {

    init {
        require(value.isNotBlank()) { "대학교 이름은 공백일 수 없습니다." }
        require(value.length <= 30) { "대학교 이름은 30자 이하여야 합니다." }
    }

}
