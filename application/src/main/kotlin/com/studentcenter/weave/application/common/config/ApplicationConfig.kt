package com.studentcenter.weave.application.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@ComponentScan(basePackages = ["com.studentcenter.weave.application"], lazyInit = true)
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = ["com.studentcenter.weave.application"])
class ApplicationConfig
