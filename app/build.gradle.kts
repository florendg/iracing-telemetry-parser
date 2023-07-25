plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":telemetry-api"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

val appMainClassName = "dev.vultureweb.iracing.telemetry.app.Main"
val appModuleName = "dev.vultureweb.iracing.telemetry.app"
val appName = "iracing-parser"
val usedJDK = JavaVersion.VERSION_20.majorVersion

val compiler = javaToolchains.compilerFor {
    languageVersion.set(JavaLanguageVersion.of(usedJDK))
}

java {
    modularity.inferModulePath.set(true);
    toolchain {
        vendor.set(JvmVendorSpec.ADOPTIUM)
        languageVersion.set(JavaLanguageVersion.of(usedJDK))
    }
}

application {
    mainClass.set(appMainClassName)
    mainModule.set(appModuleName)
}

tasks {
    test {
        useJUnitPlatform()
    }

    task<Copy>("copyDependencies") {
        from(configurations.runtimeClasspath)
        into("$buildDir/modules")
    }

    task<Exec>("package") {
        dependsOn(listOf("build", "copyDependencies"))
        val jdkHome = compiler.get().metadata.installationPath.asFile.absolutePath
        commandLine("${jdkHome}/bin/jpackage")
        args(listOf(
            "-n", "${appName}",
            "-p", "$buildDir/modules"+File.pathSeparator+"$buildDir/libs",
            "-d", "$buildDir/installer",
            "-m", "${appModuleName}/${appMainClassName}"))
    }
}




