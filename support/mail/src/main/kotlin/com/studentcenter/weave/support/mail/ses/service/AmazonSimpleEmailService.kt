package com.studentcenter.weave.support.mail.ses.service

// TODO(SES Sandbox): SES Sandbox 해제시 적용
//@Service
//class AmazonSimpleEmailService(
//    private val sesClient: SesClient,
//) {
//
//    suspend fun sendMailAsync(emailRequest: SendEmailRequest) = sesClient.use {
//        it.sendEmail(emailRequest)
//    }
//
//    fun sendMail(emailRequest: SendEmailRequest) = runBlocking { sendMailAsync(emailRequest) }
//
//}
