package com.studentcenter.weave.support.lock

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@ComponentScan(basePackages = ["com.studentcenter.weave.support.lock"])
class DistributedLockConfig (
    private val redisProperties: RedisProperties
){

    @Bean
    fun redissonClient(): RedissonClient {
        val redisConfig = Config()
        redisConfig
            .useSingleServer()
            .apply {
                address = "redis://${redisProperties.host}:${redisProperties.port}"
            }
        return Redisson.create(redisConfig)
    }

}
