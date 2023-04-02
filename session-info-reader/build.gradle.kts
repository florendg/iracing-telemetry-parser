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
    modularity.inferModulePath.set(true);
    toolchain {
        vendor.set(JvmVendorSpec.ADOPTIUM)
        languageVersion.set(JavaLanguageVersion.of(19))
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}