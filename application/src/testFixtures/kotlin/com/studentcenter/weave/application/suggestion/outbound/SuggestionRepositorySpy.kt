package com.studentcenter.weave.application.suggestion.outbound

import com.studentcenter.weave.application.suggestion.port.outbound.SuggestionRepository
import com.studentcenter.weave.domain.suggestion.entity.Suggestion
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class SuggestionRepositorySpy : SuggestionRepository {

    private val bucket = ConcurrentHashMap<UUID, Suggestion>()

    override fun save(suggestion: Suggestion) {
        bucket[suggestion.id] = suggestion
    }

    fun clear() {
        bucket.clear()
    }

    fun count() = bucket.size

}
