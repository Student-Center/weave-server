package com.studentcenter.weave.application.university.port.outbound

import com.studentcenter.weave.domain.university.entity.University
import java.util.*

interface UniversityRepository {

    fun findAll(): List<University>

    fun getById(id: UUID): University

}
