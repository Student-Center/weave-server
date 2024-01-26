package com.studentcenter.weave.bootstrap.common.config

import com.studentcenter.weave.application.common.config.ApplicationConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    value = [
        ApplicationConfig::class,
    ]
)
class WeaveHttpApplicationConfig {
}
