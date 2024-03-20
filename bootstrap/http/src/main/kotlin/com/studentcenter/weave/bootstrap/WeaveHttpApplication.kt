package com.studentcenter.weave.bootstrap

import com.studentcenter.weave.application.common.config.ApplicationConfig
import com.studentcenter.weave.infrastructure.aws.config.AwsConfig
import com.studentcenter.weave.infrastructure.client.common.config.ClientConfig
import com.studentcenter.weave.infrastructure.mail.common.config.MailConfig
import com.studentcenter.weave.infrastructure.persistence.common.config.PersistenceConfig
import com.studentcenter.weave.infrastructure.redis.common.config.RedisConfig
import com.studentcenter.weave.support.lock.DistributedLockConfig
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import java.util.*


@SpringBootApplication
@Import(
    value = [
        RedisConfig::class,
        PersistenceConfig::class,
        ApplicationConfig::class,
        MailConfig::class,
        AwsConfig::class,
        DistributedLockConfig::class,
        ClientConfig::class,
    ]
)
class WeaveHttpApplication {

    @PostConstruct
    fun started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }

}

fun main(args: Array<String>) {
    runApplication<WeaveHttpApplication>(*args)
}
