package com.studentcenter.weave.domain.user.enums

enum class Gender {
    MAN,
    WOMAN;

    fun getOppositeGender(): Gender {
        return if(this == MAN) WOMAN else MAN
    }
}
