package com.studentcenter.weave.application.university.port.outbound

import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.university.vo.UniversityName
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class UniversityRepositorySpy: UniversityRepository {

    private val bucket = ConcurrentHashMap<UUID, University>()

    fun clear() {
        bucket.clear()
    }

    fun saveAll(universities: List<University>) {
        bucket.putAll(universities.associateBy { it.id })
    }

    fun save(university: University) {
        bucket[university.id] = university
    }

    override fun findAll(): List<University> {
        return bucket.values.toList()
    }

    override fun getById(id: UUID): University {
        return bucket[id] ?: throw NoSuchElementException()
    }

    override fun getByName(name: UniversityName): University {
        return findAll().first { it.name == name }
    }

}
