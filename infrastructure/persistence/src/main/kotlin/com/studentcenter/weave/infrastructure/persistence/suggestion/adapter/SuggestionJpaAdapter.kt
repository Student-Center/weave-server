package com.studentcenter.weave.infrastructure.persistence.suggestion.adapter

import com.studentcenter.weave.application.suggestion.port.outbound.SuggestionRepository
import com.studentcenter.weave.domain.suggestion.entity.Suggestion
import com.studentcenter.weave.infrastructure.persistence.suggestion.entity.SuggestionJpaEntity.Companion.toJpaEntity
import com.studentcenter.weave.infrastructure.persistence.suggestion.repository.SuggestionJpaRepository
import org.springframework.stereotype.Component

@Component
class SuggestionJpaAdapter(
    private val suggestionJpaRepository: SuggestionJpaRepository,
) : SuggestionRepository {

    override fun save(suggestion: Suggestion) {
        suggestionJpaRepository.save(suggestion.toJpaEntity())
    }


}
