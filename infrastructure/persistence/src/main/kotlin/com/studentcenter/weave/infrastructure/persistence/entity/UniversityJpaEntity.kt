package com.studentcenter.weave.infrastructure.persistence.entity

import com.studentcenter.weave.domain.vo.UniversityName
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "university")
class UniversityJpaEntity(
    id: UUID,
    name: UniversityName,
    domainAddress: String,
    logoAddress: String,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
) {
    @Id
    var id: UUID = id
        private set

    @Column(unique = true, nullable = false)
    var name: UniversityName = name
        private set

    @Column(nullable = false, columnDefinition = "varchar(255)")
    var domainAddress: String = domainAddress
        private set

    @Column(nullable = false, columnDefinition = "varchar(255)")
    var logoAddress: String = logoAddress
        private set

    @Column(nullable = false)
    var createdAt: LocalDateTime = createdAt
        private set

    @Column(nullable = false)
    var updatedAt: LocalDateTime = updatedAt
        private set
}
