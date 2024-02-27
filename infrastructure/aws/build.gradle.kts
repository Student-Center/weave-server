import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {

    implementation(project(":support:common"))
    implementation(project(":domain"))
    implementation(project(":application"))

    implementation("org.springframework.boot:spring-boot:${Version.SPRING_BOOT}")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-s3:${Version.SPRING_CLOUD_AWS_S3}")

}
