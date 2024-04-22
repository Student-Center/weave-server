package com.studentcenter.weave.bootstrap.suggestion.controller

import com.studentcenter.weave.application.common.security.context.getCurrentUserAuthentication
import com.studentcenter.weave.application.suggestion.port.inbound.CreateSuggestion
import com.studentcenter.weave.bootstrap.suggestion.api.SuggestionApi
import com.studentcenter.weave.bootstrap.suggestion.dto.SuggestionCreateRequest
import org.springframework.web.bind.annotation.RestController

@RestController
class SuggestionRestController(
    private val createSuggestion: CreateSuggestion,
) : SuggestionApi {

    override fun createSuggestion(request: SuggestionCreateRequest) {
        request
            .toCommand(getCurrentUserAuthentication())
            .also { createSuggestion.invoke(it) }
    }

}
