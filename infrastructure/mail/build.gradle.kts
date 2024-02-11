import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    implementation(project(":support:common"))

    implementation(project(":application"))

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-mail")

    // TODO(SES Sandbox): SES Sandbox 해제시 적용
    // implementation("aws.sdk.kotlin:ses:${Version.AWS_SDK_KOTLIN_SES}")
}
