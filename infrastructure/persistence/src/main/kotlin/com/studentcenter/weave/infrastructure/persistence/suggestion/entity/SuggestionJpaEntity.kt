package com.studentcenter.weave.infrastructure.persistence.suggestion.entity

import com.studentcenter.weave.domain.suggestion.entity.Suggestion
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "suggestion")
class SuggestionJpaEntity(
    id: UUID,
    userId: UUID,
    contents: String,
) {

    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    var id: UUID = id
        private set

    @Column(name = "user_id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    var userId: UUID = userId
        private set

    @Column(name = "contents", nullable = false, length = 2000, columnDefinition = "TEXT")
    var contents: String = contents
        private set

    fun toDomainEntity() = Suggestion(
        id = id,
        userId = userId,
        contents = contents
    )

    companion object {

        fun Suggestion.toJpaEntity() = SuggestionJpaEntity(
            id = id,
            userId = userId,
            contents = contents
        )

    }

}
