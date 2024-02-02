package com.studentcenter.weave.domain.user.vo

import java.time.Year

@JvmInline
value class BirthYear(val year: Int) {

    init {
        require(year in 1900..Year.now().value) {
            "생년월일은 1900년 이후, 현재 년도 이전이어야 합니다"
        }
    }

}
