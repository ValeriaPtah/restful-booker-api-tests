plugins {
    java
}

java {
    sourceCompatibility = JavaVersion.VERSION_15
    targetCompatibility = JavaVersion.VERSION_15
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

//Versions
val commonsLang3Version = "3.12.0"
val groovyVersion = "3.0.8"
val jacksonVersion = "2.13.0"
val jaxbVersion = "2.4.0-b180830.0359"
val lombokVersion = "1.18.22"
val moshiVersion = "1.12.0"
val restAssuredVersion = "4.4.0"
val testNgVersion = "7.4.0"

dependencies {
    testImplementation("org.testng:testng:$testNgVersion")
    testImplementation("org.apache.commons:commons-lang3:$commonsLang3Version")
    testImplementation("org.projectlombok:lombok:$lombokVersion")
    testImplementation("com.squareup.moshi:moshi:$moshiVersion")
    testImplementation("com.squareup.moshi:moshi-adapters:$moshiVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:json-schema-validator:$restAssuredVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.test {
    useTestNG()
}
