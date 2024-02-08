package com.studentcenter.weave.application.university.port.outbound

import com.studentcenter.weave.domain.university.entity.Major
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MajorRepositorySpy: MajorRepository {

    private val bucket = ConcurrentHashMap<UUID, Major>()

    override fun findAllByUnivId(univId: UUID): List<Major> {
        return bucket.values.filter { it.univId == univId }.toList()
    }

    fun clear() {
        bucket.clear()
    }

    fun saveAll(majors: List<Major>) {
        bucket.putAll(majors.associateBy { it.id })
    }

}
