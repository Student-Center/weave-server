package com.studentcenter.weave.infrastructure.redis.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.studentcenter.weave.infrastructure.redis.common.properties.RedisProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
@ComponentScan(basePackages = ["com.studentcenter.weave.infrastructure.redis"])
@EnableRedisRepositories(basePackages = ["com.studentcenter.weave.infrastructure.redis"])
@ConfigurationPropertiesScan(basePackages = ["com.studentcenter.weave.infrastructure.redis.common.properties"])
class RedisConfig(
    private val redisProperties: RedisProperties,
) {

    @Bean
    fun connectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisProperties.host, redisProperties.port)
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory?): RedisTemplate<String, Any> {
        val serializer = GenericJackson2JsonRedisSerializer(objectMapper)

        return RedisTemplate<String, Any>().apply {
            connectionFactory = redisConnectionFactory!!
            keySerializer = StringRedisSerializer()
            valueSerializer = serializer
        }
    }

    private val objectMapper = ObjectMapper().apply {
        registerModule(KotlinModule.Builder().build())
        registerModule(JavaTimeModule())
    }


}
