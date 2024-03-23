package com.studentcenter.weave.infrastructure.client.discord.util

import com.studentcenter.weave.infrastructure.client.discord.common.vo.DiscordMessage
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.net.URI


@FeignClient(
    name = "discord-client",
    url = "weave-url",
)
fun interface DiscordClient {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun send(
        uri: URI,
        @RequestBody
        message: DiscordMessage,
    )

}
