package com.studentcenter.weave.domain.vo

@JvmInline
value class University(val name: String) {

    init {
        require(name.isNotBlank()) { "대학교 이름은 공백일 수 없습니다." }
        require(name.length <= 30) { "대학교 이름은 30자 이하여야 합니다." }
    }

}
