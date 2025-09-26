plugins {
    id("java")
}

group = "fr.univ_amu.m1info.mars_rover"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.20.0")
}

tasks.test {
    useJUnitPlatform()
}