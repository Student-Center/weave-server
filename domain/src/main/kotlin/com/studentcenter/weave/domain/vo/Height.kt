package com.studentcenter.weave.domain.vo

@JvmInline
value class Height (val value: Int) {

    init {
        require(value in 1..300) { "키는 1cm 이상 300cm 이하여야 합니다." }
    }

}
