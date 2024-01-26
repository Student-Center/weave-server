dependencies {
    implementation(project(":support:common"))
    implementation(project(":support:security"))
    implementation(project(":domain"))
    implementation(project(":application"))

    implementation("org.springframework.boot:spring-boot:${Version.SPRING_BOOT}")
}
