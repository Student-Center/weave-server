package com.studentcenter.weave.bootstrap.suggestion.api

import com.studentcenter.weave.bootstrap.common.security.annotation.Secured
import com.studentcenter.weave.bootstrap.suggestion.dto.SuggestionCreateRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "Suggestion", description = "Suggestion API")
@RequestMapping("/api/suggestions")
interface SuggestionApi {

    @Secured
    @Operation(summary = "Create new suggestion")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSuggestion(
        @RequestBody
        request: SuggestionCreateRequest,
    )

}
