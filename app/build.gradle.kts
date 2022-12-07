plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

application {
    mainClass.set("dev.vultureweb.iracing.telemetry.app.Main")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
