package com.studentcenter.weave.infrastructure.persistence.chat.repository

import com.studentcenter.weave.infrastructure.persistence.chat.enitty.ChatMessageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatMessageJpaRepository : JpaRepository<ChatMessageJpaEntity, UUID> {

}
