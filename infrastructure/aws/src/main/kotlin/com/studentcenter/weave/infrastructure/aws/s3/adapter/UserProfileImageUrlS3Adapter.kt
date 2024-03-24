package com.studentcenter.weave.infrastructure.aws.s3.adapter

import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPort
import com.studentcenter.weave.domain.user.entity.UserProfileImage
import com.studentcenter.weave.infrastructure.aws.s3.config.S3BucketProperties
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import java.util.*

@Component
class UserProfileImageUrlS3Adapter(
    private val s3Client: S3Client,
    private val s3PreSigner: S3Presigner,
    private val s3BucketProperties: S3BucketProperties,
) : UserProfileImageUrlPort {

    override fun getUploadImageUrl(
        imageId: UUID,
        extension: UserProfileImage.Extension
    ): Url {
        val putObjectRequest = PutObjectRequest
            .builder()
            .bucket(s3BucketProperties.userProfileImage.bucketName)
            .key(getObjectKey(imageId, extension))
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

    override fun findByIdAndExtension(
        imageId: UUID,
        extension: UserProfileImage.Extension
    ): Url {
        val getObjectRequest = GetUrlRequest
            .builder()
            .bucket(s3BucketProperties.userProfileImage.bucketName)
            .key(getObjectKey(imageId, extension))
            .build()

        return s3Client
            .utilities()
            .getUrl(getObjectRequest)
            .toString()
            .let { Url(it) }
    }

    private fun getObjectKey(
        userId: UUID,
        extension: UserProfileImage.Extension,
    ): String {
        return "${s3BucketProperties.userProfileImage.keyPrefix}${userId}.${extension.value}"
    }

    private fun getExpiredDuration(): Duration {
        return Duration.ofMinutes(s3BucketProperties.userProfileImage.presignedUrlExpirationMin)
    }

}
