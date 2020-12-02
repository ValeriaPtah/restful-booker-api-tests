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
val commonsLang3Version = "3.6"
val groovyVersion = "3.0.0"
val jacksonVersion = "2.8.6"
val jaxbVersion = "2.2.4"
val lombokVersion = "1.18.10"
val moshiVersion = "1.8.0"
val restAssuredVersion = "3.0.0"
val testNgVersion = "6.8"

dependencies {
    testImplementation("org.testng:testng:$testNgVersion")
    testImplementation("org.apache.commons:commons-lang3:$commonsLang3Version")
    testImplementation("org.codehaus.groovy:groovy-all:$groovyVersion")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    testImplementation("javax.xml.bind:jaxb-api:$jaxbVersion")
    testImplementation("org.projectlombok:lombok:$lombokVersion")
    testImplementation("com.squareup.moshi:moshi:$moshiVersion")
    testImplementation("com.squareup.moshi:moshi-adapters:$moshiVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.test {
    useTestNG()
}
