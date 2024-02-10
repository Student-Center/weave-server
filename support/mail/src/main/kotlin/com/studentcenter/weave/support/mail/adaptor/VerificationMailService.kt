package com.studentcenter.weave.support.mail.adaptor

import com.studentcenter.weave.support.common.exception.CustomException
import com.studentcenter.weave.support.common.vo.Email
import com.studentcenter.weave.support.mail.common.exception.MailExceptionType
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

@Service
class VerificationNumberMailService(
    private val javaMailSender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine,
) {
    fun send(to: Email, verificationNumber : String) {
        val mimeMessage = javaMailSender.createMimeMessage()
        MimeMessageHelper(mimeMessage, true, "UTF-8").apply {
            setTo(to.value)
            setSubject(String.format(EMAIL_TITLE_FORMAT, verificationNumber))
            setText(templateEngine.process(
                TEMPLATE_FILE_NAME,
                Context().also {
                    it.setVariable(TEMPLATE_VARIABLE_VERIFICATION_NUMBER, verificationNumber)
                },
            ), true)
        }

        try {
            javaMailSender.send(mimeMessage)
        } catch (e: MailException) {
            throw CustomException(MailExceptionType.MAIL_CLIENT_EXCEPTION, "Mail Client 이슈가 발생했습니다.")
        }
    }

    companion object {
        const val TEMPLATE_FILE_NAME = "email-verification-number"
        const val TEMPLATE_VARIABLE_VERIFICATION_NUMBER = "verificationNumber"
        const val EMAIL_TITLE_FORMAT = "\uD83D\uDD10 위브(WEAVE) 인증코드: %s"
    }
}
