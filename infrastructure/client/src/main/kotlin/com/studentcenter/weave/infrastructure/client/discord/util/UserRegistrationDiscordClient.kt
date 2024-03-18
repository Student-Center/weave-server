package com.studentcenter.weave.infrastructure.client.discord.util

import java.awt.PageAttributes
import java.util.*

@FeignClient(url = "discord.events.user-registration.url")
class UserRegistrationDiscordClient : DiscordClient {

    @PostMapping(produces = PageAttributes.MediaType.APPLICATION_JSON_VALUE)
    override fun send(
        @RequestBody
        message: String,
    )

}
