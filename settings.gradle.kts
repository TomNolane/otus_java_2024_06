rootProject.name = "otusJava"

include("hw01-gradle")
include("hw02-generics")
include("hw03-testframework")
include("hw04-gc")
include("hw04-gc2")
include("hw05-byte_codes")
include("hw07-structural_patterns")
//include("hw08-io")
//include("hw09-jdbc:homework")
//include("hw10-jpql")
//include("hw11-cache")
//include("hw12-webserver")
include("hw13-di")
include("hw14_spring_data_jdbc")
include("hw15_executors")
include("hw16-safety-collections")
include("hw17-gRPC")
include("hw18-webflux:client-service")
include("hw18-webflux:datastore-service")

pluginManagement {
    val dependencyManagement: String by settings
    val johnrengelmanShadow: String by settings
    val springframeworkBoot: String by settings
    val jib: String by settings
    val protobufVer: String by settings
    val sonarlint: String by settings
    val spotless: String by settings

    plugins {
        id("io.spring.dependency-management") version dependencyManagement
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("org.springframework.boot") version springframeworkBoot
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
    }
}
