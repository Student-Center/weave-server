package com.studentcenter.weave.infrastructure.persistence.suggestion.repository

import com.studentcenter.weave.infrastructure.persistence.suggestion.entity.SuggestionJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SuggestionJpaRepository : JpaRepository<SuggestionJpaEntity, UUID>
