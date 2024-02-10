package com.studentcenter.weave.support.email.ses.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(value = "aws.ses")
data class SesProperties(
    val accessKey: String,
    val secretKey: String,
)
