package com.studentcenter.weave.infrastructure.client.discord.util

import com.studentcenter.weave.infrastructure.client.discord.common.enums.event.DiscordEventType

interface DiscordClientFactory {

    fun getClient(discordEventType: DiscordEventType): DiscordClient

}
