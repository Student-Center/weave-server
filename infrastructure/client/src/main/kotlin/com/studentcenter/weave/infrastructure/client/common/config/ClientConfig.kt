package com.studentcenter.weave.infrastructure.client.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.studentcenter.weave.infrastructure.client"])
@EnableConfigurationProperties
@EnableFeignClients(basePackages = ["com.studentcenter.weave.infrastructure.client"])
@ConfigurationPropertiesScan(basePackages = ["com.studentcenter.weave.infrastructure.client"])
class ClientConfig {
}
