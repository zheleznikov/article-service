import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
		application
		jacoco
		id("org.springframework.boot") version "3.2.3"
		id("io.spring.dependency-management") version "1.1.4"
}

group = "com.zheleznikov"
version = "0.0.1-SNAPSHOT"

java {
		sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
		mavenCentral()
}

dependencies {
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		runtimeOnly("org.postgresql:postgresql")
		implementation("org.flywaydb:flyway-core")
		implementation("org.springframework.boot:spring-boot-starter-web")
		compileOnly("org.projectlombok:lombok")
		annotationProcessor("org.projectlombok:lombok")
		implementation("org.springframework.boot:spring-boot-starter-validation")

		implementation("org.springframework.boot:spring-boot-starter-security")

		testImplementation("org.springframework.boot:spring-boot-starter-test")

		testImplementation(platform("org.junit:junit-bom:5.10.1"))
		testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
		useJUnitPlatform()

		testLogging {
				exceptionFormat = TestExceptionFormat.FULL
				events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
				showStackTraces = true
				showCauses = true
				showStandardStreams = true
		}

		finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
		reports {
				xml.required = true
		}
		classDirectories.setFrom(files(classDirectories.files.map {
				fileTree(it) {
						setExcludes(listOf(
										"**/ArticleServiceApplication.class",
										"**/dto/**",
										"**/entity/**",
										"**/exception/**",
										"**/repository/**",
						))
				}
		}))
}
