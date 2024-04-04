package com.studentcenter.weave.infrastructure.persistence.chat.repository

import com.studentcenter.weave.infrastructure.persistence.chat.enitty.ChatRoomJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ChatRoomJpaRepository : JpaRepository<ChatRoomJpaEntity, UUID>
