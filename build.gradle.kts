plugins {
    id("java")
    id("application")



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
