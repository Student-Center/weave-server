rootProject.name = "weave-server"

// support
include(":support:common")
project(":support:common").projectDir = file("support/common")

include(":support:lock")
project(":support:lock").projectDir = file("support/lock")

include(":support:logger")
project(":support:logger").projectDir = file("support/logger")

include(":support:security")
project(":support:security").projectDir = file("support/security")

include(":support:jacoco")
project(":support:jacoco").projectDir = file("support/jacoco")

// domain
include(":domain")
project(":domain").projectDir = file("domain")

// application
include(":application")
project(":application").projectDir = file("application")

// infrastructure
include(":infrastructure:persistence")
project(":infrastructure:persistence").projectDir = file("infrastructure/persistence")

include(":infrastructure:client")
project(":infrastructure:client").projectDir = file("infrastructure/client")

include(":infrastructure:redis")
project(":infrastructure:redis").projectDir = file("infrastructure/redis")

include(":infrastructure:mail")
project(":infrastructure:mail").projectDir = file("infrastructure/mail")

include(":infrastructure:aws")
project(":infrastructure:aws").projectDir = file("infrastructure/aws")

// bootstrap
include(":bootstrap:http")
project(":bootstrap:http").projectDir = file("bootstrap/http")
