package com.studentcenter.weave.bootstrap.common.security.interceptor

import com.studentcenter.weave.application.user.service.util.UserTokenService
import com.studentcenter.weave.application.user.vo.UserAuthentication
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component

@Component
class StompAuthInterceptor(
    private val userTokenService: UserTokenService,
) : ChannelInterceptor {

    override fun preSend(
        message: Message<*>,
        channel: MessageChannel,
    ): Message<*>? {
        val accessor = StompHeaderAccessor.wrap(message)
        val command = accessor.command

        when (command) {
            StompCommand.CONNECT -> handleConnect(message)
            else -> return message
        }

        return message
    }

    private fun handleConnect(message: Message<*>) {
        val accessor = MessageHeaderAccessor
            .getAccessor(message, SimpMessageHeaderAccessor::class.java)
            ?: throw IllegalStateException("Cannot get accessor")

        extractToken(message)?.let { token ->
            val userAuthentication = userTokenService
                .resolveAccessToken(token)
                .let { UserAuthentication.from(it) }
            accessor.user = userAuthentication
        }
    }


    private fun extractToken(message: Message<*>): String? {
        val accessor = StompHeaderAccessor.wrap(message)
        val bearerToken: String? = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER)
        return if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            bearerToken.substring(BEARER_PREFIX.length)
        } else null
    }

    companion object {

        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }
}
