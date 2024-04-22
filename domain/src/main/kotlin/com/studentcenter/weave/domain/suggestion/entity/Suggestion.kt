package com.studentcenter.weave.domain.suggestion.entity

import com.studentcenter.weave.domain.common.AggregateRoot
import com.studentcenter.weave.support.common.uuid.UuidCreator
import java.util.*

data class Suggestion(
    override val id: UUID = UuidCreator.create(),
    val userId: UUID,
    val contents: String,
) : AggregateRoot {

    init {
        require(contents.isNotBlank()) {
            "내용을 입력해 주세요!"
        }

        require(contents.length <= MAX_CONTENTS_LENGTH) {
            "내용은 ${MAX_CONTENTS_LENGTH}자 이하로 입력해 주세요!"
        }
    }

    companion object {

        const val MAX_CONTENTS_LENGTH = 2000

        fun create(
            userId: UUID,
            contents: String,
        ): Suggestion {
            return Suggestion(
                userId = userId,
                contents = contents
            )
        }

    }

}
