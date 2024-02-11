package com.studentcenter.weave.bootstrap.user.dto

data class UserUnivVerificationVerifyRequest (
    val universityEmail: String,
    val verificationNumber: String,
)
