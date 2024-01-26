package com.studentcenter.weave.infrastructure.auth.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = ["com.studentcenter.weave.infrastructure.auth.common"])
@ComponentScan(basePackages = ["com.studentcenter.weave.infrastructure.auth"])
class AuthConfig
