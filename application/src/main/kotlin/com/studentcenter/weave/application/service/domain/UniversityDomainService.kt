package com.studentcenter.weave.application.service.domain

import com.studentcenter.weave.domain.entity.University
import java.util.*

interface UniversityDomainService {

    fun findAll(): List<University>

    fun getById(id: UUID): University

}
