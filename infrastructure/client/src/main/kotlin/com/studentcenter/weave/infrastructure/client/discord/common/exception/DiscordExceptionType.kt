package com.studentcenter.weave.infrastructure.client.discord.common.exception

import com.studentcenter.weave.support.common.exception.CustomExceptionType

enum class DiscordExceptionType(override val code: String) : CustomExceptionType {
    DISCORD_CLIENT_EXCEPTION("DISCORD-001"),
}
