plugins {
	java
	id("org.springframework.boot") version "3.0.4"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "com"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.projectlombok:lombok:1.18.26")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok:1.18.26")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.squareup.okhttp3:mockwebserver")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
