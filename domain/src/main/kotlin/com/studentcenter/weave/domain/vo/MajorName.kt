package com.studentcenter.weave.domain.vo

@JvmInline
value class MajorName(val value: String) {

    init {
        require(value.isNotBlank()) { "전공명은 공백일 수 없습니다." }
        require(value.length <= 30) { "전공명은 30자 이하여야 합니다." }
    }

}
