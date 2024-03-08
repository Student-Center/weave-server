package com.studentcenter.weave.infrastructure.aws.s3.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "aws-bucket")
data class S3BucketProperties(
    val userProfileImage: UserProfileImage,
) {

    data class UserProfileImage(
        val bucketName: String,
        val keyPrefix: String,
        val presignedUrlExpirationMin: Long,
    )

}
