rootProject.name = "weave-server"

// support
include(":support:common")
project(":support:common").projectDir = file("support/common")

include(":support:lock")
project(":support:lock").projectDir = file("support/lock")

include(":support:logger")
project(":support:logger").projectDir = file("support/logger")

include(":support:mail")
project(":support:mail").projectDir = file("support/mail")

include(":support:security")
project(":support:security").projectDir = file("support/security")

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

// bootstrap
include(":bootstrap:http")
project(":bootstrap:http").projectDir = file("bootstrap/http")
