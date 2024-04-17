package com.studentcenter.weave.infrastructure.persistence.user.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "pre_registered_email")
class PreRegisteredEmail(
    email: String,
) {

    @Id
    var email: String = email
        private set

}
