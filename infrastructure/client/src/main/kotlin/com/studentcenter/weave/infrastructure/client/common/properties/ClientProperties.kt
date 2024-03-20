package com.studentcenter.weave.infrastructure.client.common.properties

import com.studentcenter.weave.infrastructure.client.common.event.ClientEventType
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("client")
class ClientProperties(
    val events: Map<ClientEventType, Properties>
) {
    data class Properties(
        val url: String,
    )
}
