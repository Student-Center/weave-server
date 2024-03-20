package com.studentcenter.weave.infrastructure.client.discord.user.adaptor

import com.studentcenter.weave.application.user.port.outbound.UserEventPort
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.infrastructure.client.common.event.ClientEventType
import com.studentcenter.weave.infrastructure.client.common.properties.ClientProperties
import com.studentcenter.weave.infrastructure.client.discord.common.exception.DiscordExceptionType
import com.studentcenter.weave.infrastructure.client.discord.common.vo.DiscordMessage
import com.studentcenter.weave.infrastructure.client.discord.util.UserRegistrationDiscordClient
import com.studentcenter.weave.support.common.exception.CustomException
import org.springframework.stereotype.Component
import java.net.URI

@Component
class UserEventDiscordAdaptor(
    val userRegistrationDiscordClient: UserRegistrationDiscordClient,
    val clientProperties: ClientProperties,
) : UserEventPort {

    override fun sendRegistrationMessage(
        user: User,
        userCount: Int,
    ) {
        val discordUri =
            URI(this.clientProperties.events.getValue(ClientEventType.USER_REGISTRATION).url)
        val message = "${userCount}번째 유저 ${user.nickname.value}(${user.gender})님이 가입했어요!🎉"

        runCatching {
            userRegistrationDiscordClient.send(
                uri = discordUri,
                message = DiscordMessage(message),
            )
        }.onFailure {
            throw CustomException(
                type = DiscordExceptionType.DISCORD_CLIENT_EXCEPTION,
                message = "디스코드 Client 이슈가 발생하였습니다."
            )
        }
    }

}
