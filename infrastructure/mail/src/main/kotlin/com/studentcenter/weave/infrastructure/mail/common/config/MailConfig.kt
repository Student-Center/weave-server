package com.studentcenter.weave.infrastructure.mail.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration



@Configuration
@ComponentScan(basePackages = ["com.studentcenter.weave.infrastructure.mail"])
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = ["com.studentcenter.weave.infrastructure.mail"])
class MailConfig
