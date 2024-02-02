package com.studentcenter.weave.infrastructure.persistence.common.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ComponentScan(basePackages = ["com.studentcenter.weave.infrastructure.persistence"])
@EntityScan(basePackages = ["com.studentcenter.weave.infrastructure.persistence"])
@EnableJpaRepositories(basePackages = ["com.studentcenter.weave.infrastructure.persistence"])
class PersistenceConfig
