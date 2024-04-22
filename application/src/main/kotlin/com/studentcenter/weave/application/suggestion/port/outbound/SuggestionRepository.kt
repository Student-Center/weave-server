package com.studentcenter.weave.application.suggestion.port.outbound

import com.studentcenter.weave.domain.suggestion.entity.Suggestion

interface SuggestionRepository {

    fun save(suggestion: Suggestion)

}
