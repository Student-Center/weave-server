import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    implementation(project(":support:lock"))
    implementation(project(":support:common"))
    implementation(project(":support:security"))
    implementation(project(":domain"))

    implementation(libs.spring.tx)
    implementation(libs.spring.boot.core)
    testImplementation(testFixtures(project(":domain")))
    testFixturesImplementation(testFixtures(project(":domain")))
    testFixturesImplementation(testFixtures(project(":support:lock")))
}
