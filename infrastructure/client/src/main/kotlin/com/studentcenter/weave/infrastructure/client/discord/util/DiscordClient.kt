package com.studentcenter.weave.infrastructure.client.discord.util

import com.studentcenter.weave.infrastructure.client.discord.common.vo.DiscordMessage
import org.springframework.stereotype.Component
import java.net.URI

@Component
fun interface DiscordClient {

    fun send(
        uri: URI,
        message: DiscordMessage
    )

}
