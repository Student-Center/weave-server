package com.studentcenter.weave.application.university.service.domain

import com.studentcenter.weave.domain.university.entity.University
import com.studentcenter.weave.domain.university.vo.UniversityName
import java.util.*

interface UniversityDomainService {

    fun findAll(): List<University>

    fun getById(id: UUID): University

    fun getByName(name: UniversityName): University

}
