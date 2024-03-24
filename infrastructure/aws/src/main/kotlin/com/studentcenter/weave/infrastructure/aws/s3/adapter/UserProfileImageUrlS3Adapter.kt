package com.studentcenter.weave.infrastructure.aws.s3.adapter

import com.studentcenter.weave.application.user.port.outbound.UserProfileImageUrlPort
import com.studentcenter.weave.application.user.vo.ImageFileExtension
import com.studentcenter.weave.infrastructure.aws.s3.config.S3BucketProperties
import com.studentcenter.weave.support.common.vo.Url
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Object
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

    override fun findAllByUserId(userId: UUID): List<Url> {
        return listObjectsByUserId(userId)
            .map { it.key() }
            .map { createUrlFromS3Object(it) }
    }

    override fun deleteByUrl(url: Url) {
        val key: String = url.value.substringAfter("amazonaws.com/")
        s3Client.deleteObject {
            it.bucket(s3BucketProperties.userProfileImage.bucketName)
            it.key(key)
        }

    }


    private fun listObjectsByUserId(userId: UUID): List<S3Object> {
        val listObjectsRequest: ListObjectsV2Request = createListObjectsRequest(userId)
        val result: ListObjectsV2Response = s3Client.listObjectsV2(listObjectsRequest)
        return result.contents()
    }

    private fun createListObjectsRequest(userId: UUID): ListObjectsV2Request {
        return ListObjectsV2Request
            .builder()
            .bucket(s3BucketProperties.userProfileImage.bucketName)
            .prefix(getPrefixByUserId(userId))
            .build()
    }

    private fun createUrlFromS3Object(key: String): Url {
        val getUrlRequest = GetUrlRequest.builder()
            .bucket(s3BucketProperties.userProfileImage.bucketName)
            .key(key)
            .build()
        return s3Client.utilities().getUrl(getUrlRequest).let { Url(it.toString()) }
    }


    private fun getPrefixByUserId(userId: UUID): String {
        return "${s3BucketProperties.userProfileImage.keyPrefix}${userId}."
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
