package com.studentcenter.weave.bootstrap

import com.studentcenter.weave.bootstrap.common.config.WeaveHttpApplicationConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(value = [WeaveHttpApplicationConfig::class] )
class WeaveHttpApplication

fun main(args: Array<String>) {
    runApplication<WeaveHttpApplication>(*args)
}
