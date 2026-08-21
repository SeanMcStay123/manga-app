plugins {
    kotlin("jvm") version "2.4.0"
}

group = "ie.setu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test")) // exists only for testing code

    // dependencies for logging
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    // for streaming to XML and JSON
    implementation("com.thoughtworks.xstream:xstream:1.4.18")
    implementation("org.codehaus.jettison:jettison:1.4.1")
}

// slf4j-simple is a standard logging interface that Java/Kotlin libraries agree on, kotlin-logging wouldnt work properly without it

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}