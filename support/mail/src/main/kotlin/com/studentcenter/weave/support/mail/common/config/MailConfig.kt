package com.studentcenter.weave.support.mail.common.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration



@Configuration
@ComponentScan(basePackages = ["com.studentcenter.weave.support.mail"])
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = ["com.studentcenter.weave.support.mail"])
class MailConfig
