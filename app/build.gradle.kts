plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

java {
    modularity.inferModulePath.set(true);
}
application {
    mainClass.set("dev.vultureweb.iracing.telemetry.app.Main")
    mainModule.set("dev.vultureweb.iracing.telemetry.app")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
