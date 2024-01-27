import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    implementation(project(":support:common"))

    implementation(project(":domain"))
    implementation(project(":application"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${Version.SPRING_BOOT}")
    runtimeOnly("mysql:mysql-connector-java:${Version.MYSQL}")
    testRuntimeOnly("com.h2database:h2:${Version.H2}")
}
