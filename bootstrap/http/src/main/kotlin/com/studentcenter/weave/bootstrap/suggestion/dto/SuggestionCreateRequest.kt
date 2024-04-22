package com.studentcenter.weave.bootstrap.suggestion.dto

import com.studentcenter.weave.application.suggestion.port.inbound.CreateSuggestion
import com.studentcenter.weave.application.user.vo.UserAuthentication

data class SuggestionCreateRequest(
    val contents: String?,
) {

    fun toCommand(userAuth: UserAuthentication): CreateSuggestion.Command {
        require(contents != null) {
            "내용을 입력해 주세요!"
        }

        return CreateSuggestion.Command(
            userId = userAuth.userId,
            contents = contents
        )
    }
}
