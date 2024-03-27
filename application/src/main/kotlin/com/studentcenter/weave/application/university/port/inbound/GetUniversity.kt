package com.studentcenter.weave.application.university.port.inbound

import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.university.vo.UniversityName
import java.util.*

interface GetUniversity {

    fun findAll(): List<University>

    fun getById(id: UUID): University

    fun getByName(name: UniversityName): University

}
