plugins {
    id("java")
    application

    id("org.openjfx.javafxplugin") version "0.0.14"
}

group = "simulacion.continuo"
version = "1.0-SNAPSHOT"


javafx {
    
    version = "17"
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("simulacion.continuo.Main")
}

repositories {
    mavenCentral()
}

dependencies {
    
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("org.json:json:20240303")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

