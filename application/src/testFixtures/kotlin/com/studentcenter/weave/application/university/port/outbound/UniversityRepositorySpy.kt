package com.studentcenter.weave.application.university.port.outbound

import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.support.common.exception.CustomException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UniversityRepositorySpy: UniversityRepository {

    private val bucket = ConcurrentHashMap<UUID, University>()

    fun clear() {
        bucket.clear()
    }

    fun saveAll(majors: List<University>) {
        bucket.putAll(majors.associateBy { it.id })
    }

    override fun findAll(): List<University> {
        return bucket.values.toList()
    }

    override fun getById(id: UUID): University {
        return bucket[id] ?: throw IllegalArgumentException()
    }

}
