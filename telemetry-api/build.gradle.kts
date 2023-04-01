plugins {
    `java-library`
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

tasks {
    test {
        useJUnitPlatform()
    }
}