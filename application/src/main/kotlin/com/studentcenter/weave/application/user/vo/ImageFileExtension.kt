package com.studentcenter.weave.application.user.vo

@JvmInline
value class ImageFileExtension(val value: String) {

    init {
        require(value.matches(Regex(VALIDATION_REGEX))) {
            "${value}: 이미지 파일 확장자는 jpg, jpeg, png, gif, svg, webp 중 하나여야 합니다."
        }
    }

    companion object {

        const val VALIDATION_REGEX = "\\.(jpg|jpeg|png|gif|svg|webp)\$"

    }

}
