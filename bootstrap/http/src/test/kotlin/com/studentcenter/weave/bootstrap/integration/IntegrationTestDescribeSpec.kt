package com.studentcenter.weave.bootstrap.integration

import io.kotest.core.spec.style.DescribeSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.ComposeContainer
import java.io.File

@SpringBootTest
@ActiveProfiles("integration-test")
abstract class IntegrationTestDescribeSpec(body: DescribeSpec.() -> Unit = {}) :
    DescribeSpec(body) {

    companion object {

        private val container: ComposeContainer =
            ComposeContainer(File("src/test/resources/compose-test.yaml"))
                .withExposedService("mysql", 3306)
                .withExposedService("redis", 6379)


        init {
            container.start()
        }

    }

}
