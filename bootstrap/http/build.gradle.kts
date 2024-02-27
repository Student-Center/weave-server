dependencies {
    implementation(project(":support:common"))
    implementation(project(":support:lock"))
    implementation(project(":support:security"))

    implementation(project(":domain"))
    implementation(project(":application"))
    implementation(project(":infrastructure:client"))
    implementation(project(":infrastructure:persistence"))
    implementation(project(":infrastructure:redis"))
    implementation(project(":infrastructure:mail"))
    implementation(project(":infrastructure:aws"))

    implementation("org.springframework.boot:spring-boot-starter-web:${Version.SPRING_BOOT}")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${Version.SPRINGDOC_OPENAPI}")

    developmentOnly("org.springframework.boot:spring-boot-devtools:${Version.SPRING_BOOT}")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose:${Version.SPRING_BOOT}")

    testImplementation(testFixtures(project(":application")))
    testImplementation(testFixtures(project(":domain")))
}
