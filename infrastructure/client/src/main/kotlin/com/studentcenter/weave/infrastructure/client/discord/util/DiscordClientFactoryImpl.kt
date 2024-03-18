package com.studentcenter.weave.infrastructure.client.discord.util

import com.studentcenter.weave.infrastructure.client.discord.common.enums.event.DiscordEventType

class DiscordClientFactoryImpl(
    private val applicationContext: ApplicationContext,
) : DiscordClientFactory {

    override fun getClient(discordEventType: DiscordEventType): DiscordClient {
        return when (discordEventType) {
            DiscordEventType.USER_REGISTRATION -> applicationContext.getBean(
                UserRegistrationDiscordClient::class.java
            )
        }
    }

}
