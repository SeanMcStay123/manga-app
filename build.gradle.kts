plugins {
    kotlin("jvm") version "2.4.0" // plugin for Dokka and KDoc generating tool
    id("org.jetbrains.dokka") version "1.9.20"
    jacoco
    // Plugin for Ktlint
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
    application
}
group = "ie.setu"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test")) // exists only for testing code

    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0") // dependencies for logging
    implementation("org.slf4j:slf4j-simple:2.0.16")

    implementation("com.thoughtworks.xstream:xstream:1.4.18") // for streaming to XML and JSON
    implementation("org.codehaus.jettison:jettison:1.4.1")

    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.20") // For generating a Dokka Site from KDoc
}

// slf4j-simple is a standard logging interface that Java/Kotlin libraries agree on, kotlin-logging wouldnt work properly without it

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
    // report is always generated after tests run
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jar {
    // for building a fat jar and include all dependencies
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}
