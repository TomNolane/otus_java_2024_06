import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id ("com.github.johnrengelman.shadow")
    id("idea")
}

dependencies {
    // Spring Boot WebFlux (реактивное API)
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Netty для работы с UDP
    implementation("io.projectreactor.netty:reactor-netty:1.1.10")
    implementation("io.netty:netty-all:4.1.100.Final")

    // PostgreSQL драйвер
    implementation("org.postgresql:postgresql")

    // R2DBC (реактивный драйвер для PostgreSQL)
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("io.r2dbc:r2dbc-postgresql")

    // RabbitMQ
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    // Thymeleaf для дашбордов
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Тестирование
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("app")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "tomnolane.otus.loganalytics.LogAnalyticsApplication"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}