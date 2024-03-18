package com.studentcenter.weave.infrastructure.client.discord.common.config

@Configuration
@ComponentScan(basePackages = ["com.studentcenter.weave.infrastructure.client"])
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = ["com.studentcenter.weave.infrastructure.client"])
class DiscordConfig {
}
