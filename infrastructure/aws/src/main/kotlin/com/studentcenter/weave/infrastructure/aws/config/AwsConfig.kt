package com.studentcenter.weave.infrastructure.aws.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.studentcenter.weave.infrastructure.aws"])
@ConfigurationPropertiesScan(basePackages = ["com.studentcenter.weave.infrastructure.aws"])
class AwsConfig
