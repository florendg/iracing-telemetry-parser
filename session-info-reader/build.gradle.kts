plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

java {
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