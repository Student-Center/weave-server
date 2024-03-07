package com.studentcenter.weave.infrastructure.aws.s3.adapter

import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPort
import com.studentcenter.weave.application.user.vo.ImageFileExtension
import com.studentcenter.weave.infrastructure.aws.s3.config.S3BucketProperties
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import java.util.*

@Component
class UserProfileImageUrlS3Adapter(
    private val s3PreSigner: S3Presigner,
    private val s3BucketProperties: S3BucketProperties,
) : UserProfileImageUrlPort {

    override fun getUploadUrlByUserId(
        userId: UUID,
        imageFileExtension: ImageFileExtension,
    ): Url {
        val putObjectRequest = PutObjectRequest
            .builder()
            .bucket(s3BucketProperties.userProfileImage.bucketName)
            .key(getObjectKey(userId, imageFileExtension))
            .build()

        val preSignRequest = PutObjectPresignRequest
            .builder()
            .signatureDuration(getExpiredDuration())
            .putObjectRequest(putObjectRequest)
            .build()

        return s3PreSigner
            .presignPutObject(preSignRequest)
            .url()
            .toString()
            .let { Url(it) }
    }

    private fun getObjectKey(
        userId: UUID,
        imageFileExtension: ImageFileExtension,
    ): String {
        return "${s3BucketProperties.userProfileImage.keyPrefix}${userId}.${imageFileExtension.value}"
    }

    private fun getExpiredDuration(): Duration {
        return Duration.ofMinutes(s3BucketProperties.userProfileImage.presignedUrlExpirationMin)
    }

}
