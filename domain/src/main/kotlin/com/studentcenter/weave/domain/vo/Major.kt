package com.studentcenter.weave.domain.vo

@JvmInline
value class Major(val name: String) {

    init {
        require(name.isNotBlank()) { "전공명은 공백일 수 없습니다." }
        require(name.length <= 20) { "전공명은 30자 이하여야 합니다." }
    }

}
