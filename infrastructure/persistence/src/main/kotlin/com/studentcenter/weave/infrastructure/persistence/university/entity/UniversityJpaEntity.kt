package com.studentcenter.weave.infrastructure.persistence.university.entity

import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.university.vo.UniversityName
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
    name: String,
    displayName: String,
    domainAddress: String,
    logoAddress: String?,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
) {

    @Id
    var id: UUID = id
        private set

    @Column(unique = true, nullable = false)
    var name: String = name
        private set

    @Column(nullable = false)
    var displayName: String = displayName
        private set

    @Column(nullable = false, columnDefinition = "varchar(255)")
    var domainAddress: String = domainAddress
        private set

    @Column(nullable = true, columnDefinition = "varchar(255)")
    var logoAddress: String? = logoAddress
        private set

    @Column(nullable = false)
    var createdAt: LocalDateTime = createdAt
        private set

    @Column(nullable = false)
    var updatedAt: LocalDateTime = updatedAt
        private set

    fun toDomain() : University {
        return University(
            id = this.id,
            name = UniversityName(value = this.name),
            displayName = displayName,
            domainAddress = this.domainAddress,
            logoAddress = this.logoAddress,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
    }

}
