dependencies {
    implementation(project(":application"))
    implementation(project(":infrastructure:client"))
    implementation(project(":infrastructure:persistence"))

    implementation("org.springframework.boot:spring-boot-starter-web:${Version.SPRING_BOOT}")
}
