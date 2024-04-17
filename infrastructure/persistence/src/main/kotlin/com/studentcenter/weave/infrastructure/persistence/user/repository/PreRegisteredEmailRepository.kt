package com.studentcenter.weave.infrastructure.persistence.user.repository

import com.studentcenter.weave.infrastructure.persistence.user.entity.PreRegisteredEmail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreRegisteredEmailRepository: JpaRepository<PreRegisteredEmail, String>
