import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    implementation(project(":support:common"))
    // 메일 TEMPLATE 사용을 위한 thymeleaf
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    implementation("aws.sdk.kotlin:ses:${Version.AWS_SDK_KOTLIN_SES}") {
        exclude("com.squareup.okhttp3:okhttp")
    }
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.12")
}
