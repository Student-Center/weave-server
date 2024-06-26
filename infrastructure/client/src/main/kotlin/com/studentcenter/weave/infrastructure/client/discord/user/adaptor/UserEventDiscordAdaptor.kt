package com.studentcenter.weave.infrastructure.client.discord.user.adaptor

import com.studentcenter.weave.application.user.port.outbound.UserEventPort
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.infrastructure.client.common.event.EventType
import com.studentcenter.weave.infrastructure.client.common.properties.ClientProperties
import com.studentcenter.weave.infrastructure.client.discord.common.vo.DiscordMessage
import com.studentcenter.weave.infrastructure.client.discord.util.DiscordClient
import org.springframework.stereotype.Component
import java.net.URI

@Component
class UserEventDiscordAdaptor(
    val discordClient: DiscordClient,
    val clientProperties: ClientProperties,
) : UserEventPort {

    override fun sendRegistrationMessage(
        user: User,
        userCount: Int,
    ) {
        if (this.clientProperties.events.getValue(EventType.USER_REGISTRATION).active) {
            val discordUri =
                URI(this.clientProperties.events.getValue(EventType.USER_REGISTRATION).url)
            val prefix = if (userCount % 10 == 0) "@everyone\n" else ""
            val message = "$prefix${userCount}번째 유저 ${user.nickname.value}(${user.gender})님이 가입했어요!🎉"

            runCatching {
                discordClient.send(
                    uri = discordUri,
                    message = DiscordMessage(message),
                )
            }.onFailure {
                // TODO: 로깅 시스템 도입 시 로그 추가.
            }
        }
    }

}
