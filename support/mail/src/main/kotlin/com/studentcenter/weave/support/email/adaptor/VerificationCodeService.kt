package com.studentcenter.weave.support.email.adaptor

import aws.sdk.kotlin.services.ses.model.Body
import aws.sdk.kotlin.services.ses.model.Content
import aws.sdk.kotlin.services.ses.model.Destination
import aws.sdk.kotlin.services.ses.model.Message
import aws.sdk.kotlin.services.ses.model.SendEmailRequest
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.email.ses.service.AmazonSimpleEmailService
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

@Service
class VerificationCodeService(
    private val amazonSimpleEmailService: AmazonSimpleEmailService,
    private val templateEngine: SpringTemplateEngine,
) {

    suspend fun sendVerificationCodeAsync(to: Email, code : String) {
        createEmailRequest(to, code).let {
            amazonSimpleEmailService.sendMailAsync(emailRequest = it)
        }

    }

    fun sendVerificationCode(to: Email, code : String) {
        createEmailRequest(to, code).let {
            amazonSimpleEmailService.sendMail(emailRequest = it)
        }
    }

    private fun createEmailRequest(
        to: Email,
        code: String
    ): SendEmailRequest {
        return SendEmailRequest {
            source = DEFAULT_SENDER_EMAIL
            // 메일 받는 사람
            destination = Destination {
                toAddresses = listOf(to.value)
            }
            // 메일 메시지 생성
            message = Message {
                // 메일 제목
                subject = Content {
                    data = String.format(EMAIL_TITLE_FORMAT, code)
                }

                // 메일 내용
                body = Body {
                    html = Content {
                        data = templateEngine.process(
                            TEMPLATE_FILE_NAME,
                            Context().also { it.setVariable("code", code) },
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val DEFAULT_SENDER_EMAIL = "studentcenter.weave@gmail.com"
        const val TEMPLATE_FILE_NAME = "email-verification-number"
        const val EMAIL_TITLE_FORMAT = "\uD83D\uDD10 위브(WEAVE) 인증코드: %s"
    }
}
