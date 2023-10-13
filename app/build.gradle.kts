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
val usedJDK = JavaVersion.VERSION_21.majorVersion

val compiler = javaToolchains.compilerFor {
    languageVersion.set(JavaLanguageVersion.of(usedJDK))
}

java {
    modularity.inferModulePath.set(true)
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(usedJDK))
    }
}

application {
    mainClass.set(appMainClassName)
    mainModule.set(appModuleName)
    applicationDefaultJvmArgs = listOf("--enable-preview");
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<JavaCompile>() {
        options.compilerArgs.addAll(listOf("--enable-preview"))
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
                "-n", appName,
                "--java-options", "--enable-preview",
                "-p", "$buildDir/modules" + File.pathSeparator + "$buildDir/libs",
                "-d", "$buildDir/installer",
                "-m", "${appModuleName}/${appMainClassName}"))
    }
}




