package com.studentcenter.weave.infrastructure.client.common.properties

import com.studentcenter.weave.infrastructure.client.common.event.EventType
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("client")
class ClientProperties(
    val events: Map<EventType, Properties>
) {
    data class Properties(
        val active: Boolean,
        val url: String,
    )
}
