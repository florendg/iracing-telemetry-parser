plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":session-info-reader"))
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

java {
    modularity.inferModulePath.set(true)
    toolchain {
        vendor.set(JvmVendorSpec.ADOPTIUM)
        languageVersion.set(JavaLanguageVersion.of(20))
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}