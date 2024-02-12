package com.studentcenter.weave.infrastructure.mail.adaptor

import com.studentcenter.weave.application.user.port.outbound.VerificationNumberMailer
import com.studentcenter.weave.application.user.vo.UserUniversityVerificationNumber
import com.studentcenter.weave.infrastructure.mail.common.exception.MailExceptionType
import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Email
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import kotlin.time.Duration

@Component
class VerificationNumberGmailAdaptor(
    private val javaMailSender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine,
): VerificationNumberMailer {
    override fun send(
        to: Email,
        verificationNumber: UserUniversityVerificationNumber,
        expirationDuration: Duration,
    ) {
        val mimeMessage = javaMailSender.createMimeMessage()
        MimeMessageHelper(mimeMessage, true, "UTF-8").apply {
            setTo(to.value)
            setSubject("\uD83D\uDD10 위브(WEAVE) 인증코드: ${verificationNumber.value}")
            setText(createText(verificationNumber, expirationDuration), true)
        }

        try {
            javaMailSender.send(mimeMessage)
        } catch (e: MailException) {
            println(e.message)
            throw CustomException(MailExceptionType.MAIL_CLIENT_EXCEPTION, "Mail Client 이슈가 발생했습니다.")
        }
    }

    private fun createText(verificationNumber: UserUniversityVerificationNumber, expirationDuration: Duration): String {
        return templateEngine.process(TEMPLATE_FILE_NAME, Context().also {
            it.setVariable("expirationMinute", expirationDuration.inWholeMinutes)
            it.setVariable("verificationNumber", verificationNumber.value)
        })
    }

    companion object {
        const val TEMPLATE_FILE_NAME = "email-verification-number"
    }

}
