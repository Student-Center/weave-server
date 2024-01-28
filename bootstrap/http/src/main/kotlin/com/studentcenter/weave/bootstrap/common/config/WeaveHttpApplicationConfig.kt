package com.studentcenter.weave.bootstrap.common.config

import com.studentcenter.weave.application.common.config.ApplicationConfig
import com.studentcenter.weave.infrastructure.persistence.common.config.PersistenceConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    value = [
        PersistenceConfig::class,
        ApplicationConfig::class,
    ]
)
class WeaveHttpApplicationConfig
