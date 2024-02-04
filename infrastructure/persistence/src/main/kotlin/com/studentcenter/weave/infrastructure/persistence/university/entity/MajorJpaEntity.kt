package com.studentcenter.weave.infrastructure.persistence.university.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "major")
class MajorJpaEntity(
    id: UUID,
    univId: UUID,
    name: String,
    createdAt: LocalDateTime,
) {

    @Id
    var id: UUID = id
        private set

    @Column(nullable = false)
    var univId: UUID = univId
        private set

    @Column(nullable = false)
    var name: String = name
        private set

    @Column(nullable = false)
    var createdAt: LocalDateTime = createdAt
        private set
}
