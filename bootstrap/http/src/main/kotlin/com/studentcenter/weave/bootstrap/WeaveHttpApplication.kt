package com.studentcenter.weave.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeaveHttpApplication

fun main(args: Array<String>) {
    runApplication<WeaveHttpApplication>(*args)
}
