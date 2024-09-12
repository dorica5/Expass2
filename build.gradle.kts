plugins {
    java
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "hvl.dat250"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation ("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    implementation("org.junit.jupiter:junit-jupiter:5.11.0")
    implementation("org.springframework.boot:spring-boot-test:3.3.3")
    implementation("org.springframework:spring-webflux:6.1.12")




}

tasks.withType<Test> {
    useJUnitPlatform()
}
