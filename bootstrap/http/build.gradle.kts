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

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.websocket)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.springdoc.openapi.starter.webmvc.ui)

    implementation(libs.sentry.spring.boot.starter.jarkarta)
    implementation(libs.sentry.logback)

    developmentOnly(libs.spring.boot.devtools)
    developmentOnly(libs.spring.boot.docker.compose)

    testImplementation(testFixtures(project(":application")))
    testImplementation(testFixtures(project(":domain")))
}
