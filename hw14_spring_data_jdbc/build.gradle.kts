dependencies {
    dependencies {
        implementation ("ch.qos.logback:logback-classic")
        implementation("com.google.code.gson:gson")
        implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.flywaydb:flyway-core")
        runtimeOnly("org.postgresql:postgresql")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.assertj:assertj-core:3.25.1")
    }
}