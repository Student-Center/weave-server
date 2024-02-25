package com.studentcenter.weave.bootstrap.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig(
    @Value("\${swagger.server.url:http://localhost:8080}")
    private val serverUrl: String,
) {

    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .components(securityComponents())
            .servers(
                listOf(Server().apply { url = serverUrl })
            )
    }

    private fun securityComponents(): Components {
        return Components()
            .addSecuritySchemes(
            "AccessToken",
            SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
        )
    }

}
