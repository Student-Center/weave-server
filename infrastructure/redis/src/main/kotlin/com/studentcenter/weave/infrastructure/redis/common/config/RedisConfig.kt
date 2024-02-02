package com.studentcenter.weave.infrastructure.redis.common.config

import com.studentcenter.weave.infrastructure.redis.common.properties.RedisProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories


@Configuration
@ComponentScan(basePackages = ["com.studentcenter.weave.infrastructure.redis"])
@EnableRedisRepositories(basePackages = ["com.studentcenter.weave.infrastructure.redis"])
@ConfigurationPropertiesScan(basePackages = ["com.studentcenter.weave.infrastructure.redis.common.properties"])
class RedisConfig(
    private val redisProperties: RedisProperties
) {

    @Bean
    fun connectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory?): RedisTemplate<*, *> {
        val template = RedisTemplate<ByteArray, ByteArray>()
        template.connectionFactory = redisConnectionFactory
        return template
    }

}
