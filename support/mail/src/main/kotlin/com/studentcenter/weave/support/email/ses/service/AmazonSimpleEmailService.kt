package com.studentcenter.weave.support.email.ses.service

import aws.sdk.kotlin.services.ses.SesClient
import aws.sdk.kotlin.services.ses.model.SendEmailRequest
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class AmazonSimpleEmailService(
    private val sesClient: SesClient,
) {

    suspend fun sendMailAsync(emailRequest: SendEmailRequest) = sesClient.use {
        it.sendEmail(emailRequest)
    }

    fun sendMail(emailRequest: SendEmailRequest) = runBlocking { sendMailAsync(emailRequest) }

}
