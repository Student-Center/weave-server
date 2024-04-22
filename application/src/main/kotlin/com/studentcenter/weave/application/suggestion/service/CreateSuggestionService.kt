package com.studentcenter.weave.application.suggestion.service

import com.studentcenter.weave.application.suggestion.port.inbound.CreateSuggestion
import com.studentcenter.weave.application.suggestion.port.outbound.SuggestionRepository
import com.studentcenter.weave.domain.suggestion.entity.Suggestion
import org.springframework.stereotype.Service

@Service
class CreateSuggestionService(
    private val suggestionRepository: SuggestionRepository,
) : CreateSuggestion {

    override fun invoke(command: CreateSuggestion.Command) {
        Suggestion.create(
            userId = command.userId,
            contents = command.contents
        ).also {
            suggestionRepository.save(it)
        }
    }

}
