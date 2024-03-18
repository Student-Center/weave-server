package com.studentcenter.weave.infrastructure.client.discord.user.adaptor

import com.studentcenter.weave.application.user.port.outbound.UserEventPort
import com.studentcenter.weave.domain.user.entity.User
import com.studentcenter.weave.infrastructure.client.discord.common.enums.exception.DiscordExceptionType
import com.studentcenter.weave.infrastructure.client.discord.util.DiscordClient
import com.studentcenter.weave.support.common.exception.CustomException

@Component
class UserEventDiscordAdaptor(
    private val discordClient: DiscordClient,
) : UserEventPort {

    override fun sendRegistrationMessage(user: User) {
        val discordMessage = "${user.nickname} 님의 신규 가입을 환영해요!"

        runCatching {
            discordClient.send(discordMessage)
        }.onFailure {
            throw CustomException(
                type = DiscordExceptionType.DISCORD_CLIENT_EXCEPTION,
                message = "디스코드 Client 이슈가 발생하였습니다."
            )
        }
    }

}
