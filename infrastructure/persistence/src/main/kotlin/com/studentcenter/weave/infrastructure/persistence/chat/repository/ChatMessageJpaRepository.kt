package com.studentcenter.weave.infrastructure.persistence.chat.repository

import com.studentcenter.weave.infrastructure.persistence.chat.enitty.ChatMessageJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ChatMessageJpaRepository : JpaRepository<ChatMessageJpaEntity, UUID> {

    @Query(
        value =
        """
        select * from chat_message cm
        where cm.room_id = :chatRoomId
        and (:next is null or cm.id <= :next)
        order by cm.id desc
        limit :limit
        """,
        nativeQuery = true
    )
    fun getScrollList(
        chatRoomId: UUID,
        next: UUID?,
        limit: Int,
    ): List<ChatMessageJpaEntity>

}
