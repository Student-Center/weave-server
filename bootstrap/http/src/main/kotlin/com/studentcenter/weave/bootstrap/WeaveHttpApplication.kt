package com.studentcenter.weave.bootstrap

import com.studentcenter.weave.application.common.config.ApplicationConfig
import com.studentcenter.weave.infrastructure.persistence.common.config.PersistenceConfig
import com.studentcenter.weave.infrastructure.redis.common.config.RedisConfig
import com.studentcenter.weave.infrastructure.mail.common.config.MailConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(
    value = [
        RedisConfig::class,
        PersistenceConfig::class,
        ApplicationConfig::class,
        MailConfig::class,
    ]
)
class WeaveHttpApplication

fun main(args: Array<String>) {
    runApplication<WeaveHttpApplication>(*args)
}
