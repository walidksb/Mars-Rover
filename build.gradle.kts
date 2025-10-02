plugins {
    id("java")
    id("application")
    id("jacoco")
    id("org.barfuin.gradle.jacocolog") version "3.1.0"



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
    testImplementation("org.assertj:assertj-core:3.27.3")


}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
application {
    mainClass.set("fr.univ_amu.m1info.mars_rover.simulator.App")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "fr.univ_amu.m1info.mars_rover.simulator.App"
    }
}
tasks.jar {
    archiveBaseName.set("mars-rover")
    archiveVersion.set("")
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}