import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    implementation(project(":support:common"))
    implementation(project(":domain"))
    implementation(project(":application"))

    implementation(libs.spring.boot.core)
    implementation(libs.spring.cloud.starter.aws.s3)
}
