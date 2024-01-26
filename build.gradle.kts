import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("jacoco")
    id("jacoco-report-aggregation")
    id("java-test-fixtures")
    id("org.sonarqube") version Version.SONAR_CLOUD
    id("org.springframework.boot") version Version.SPRING_BOOT
    id("io.spring.dependency-management") version Version.SPRING_BOOT_DEPENDENCY_MANAGEMENT
    kotlin("jvm") version Version.KOTLIN
    kotlin("plugin.jpa") version Version.KOTLIN
    kotlin("plugin.spring") version Version.KOTLIN
}

allprojects {
    group = "com.student-center"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "jacoco")
    apply(plugin = "java-test-fixtures")
    apply(plugin = "org.sonarqube")
    apply(plugin = "idea")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-noarg")
    apply(plugin = "kotlin-allopen")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:${Version.KOTLIN}")
        implementation("org.jetbrains.kotlin:kotlin-reflect:${Version.KOTLIN}")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Version.JACKSON}")
        implementation("com.fasterxml.jackson.core:jackson-databind:${Version.JACKSON}")

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:${Version.SPRING_BOOT}")

        testImplementation("io.mockk:mockk:${Version.MOCKK}")
        testImplementation("com.ninja-squad:springmockk:${Version.SPRING_MOCKK}")
        testImplementation("io.kotest:kotest-runner-junit5:${Version.KOTEST}")
        testImplementation("io.kotest:kotest-assertions-core:${Version.KOTEST}")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:${Version.KOTEST_EXTENSIONS_SPRING}")
        testImplementation("org.springframework.boot:spring-boot-starter-test:${Version.SPRING_BOOT}")
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(Version.JAVA))
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = Version.JAVA
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}

sonar {
    properties {
        property("sonar.projectKey", "Student-Center_weave-server")
        property("sonar.organization", "student-center")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}


val allProjects = project.allprojects
    .asSequence()
    .filter { it.name != "weave-server" }
    .filter { it.name != "support" }
    .filter { it.name != "domain" }
    .filter { it.name != "application" }
    .filter { it.name != "infrastructure" }
    .filter { it.name != "bootstrap" }
    .toList()

project(":support:jacoco") {
    apply(plugin = "jacoco")
    apply(plugin = "jacoco-report-aggregation")

    dependencies {
        allProjects.forEach {
            add("jacocoAggregation", project(it.path))
        }
    }
}
