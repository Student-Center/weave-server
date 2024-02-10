package com.studentcenter.weave.support.email.ses.config

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.ses.SesClient
import com.studentcenter.weave.support.email.ses.properties.SesProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["com.studentcenter.weave.support.email"])
@ConfigurationPropertiesScan(basePackages = ["com.studentcenter.weave.support.email"])
class SesConfig(
    private val sesProperties: SesProperties,
) {

    @Bean
    fun sesClient() = SesClient {
        // region 설정
        region = "ap-northeast-2"

        // aws 인증
        credentialsProvider = StaticCredentialsProvider {
            accessKeyId = sesProperties.accessKey
            secretAccessKey = sesProperties.secretKey
        }


    }

}
