import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Properties

plugins {
    id("java")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClass = "org.example.Main"
}

repositories {
    mavenCentral()
}

dependencies {
    // Apache Commons Lang3
    implementation("org.apache.commons:commons-lang3:3.14.0")

    // Логирование
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("org.slf4j:slf4j-api:2.0.11")

    // Тестирование
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(project(":string-utils"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    manifest {
        attributes(Pair("Main-Class", "org.example.Main"))
    }
    archiveClassifier.set("all")
}


abstract class PrintInfoTask : DefaultTask() {
    @TaskAction
    fun print() {
        println("=============================")
        println("Это моя первая пользовательская задача!")
        println("Проект: ${project.name}")
        println("Версия Gradle: ${project.gradle.gradleVersion}")
        println("=============================")
    }
}

tasks.register<PrintInfoTask>("printInfo") {
    group = "Custom"
    description = "Выводит информацию о проекте"
}


abstract class GenerateBuildPassportTask : DefaultTask() {
    @TaskAction
    fun generate() {
        val props = Properties()

        val userName = System.getenv("USER") ?: System.getenv("USERNAME") ?: "unknown"
        props.setProperty("build.user", userName)

        val osName = System.getProperty("os.name")
        props.setProperty("build.os", osName)

        val javaVersion = System.getProperty("java.version")
        props.setProperty("build.java.version", javaVersion)

        val now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        props.setProperty("build.datetime", now)

        props.setProperty("build.message", "Project  ${project.name} compiled!")

        val buildNumberFile = project.rootProject.projectDir.resolve("build.number")
        var buildNumber = 1
        if (buildNumberFile.exists()) {
            buildNumber = buildNumberFile.readText().trim().toInt() + 1
        }
        buildNumberFile.writeText(buildNumber.toString())
        props.setProperty("build.number", buildNumber.toString())

        val resourcesDir = project.projectDir.resolve("src/main/resources")
        resourcesDir.mkdirs()
        val propFile = resourcesDir.resolve("build-passport.properties")

        propFile.outputStream().use { outputStream ->
            props.store(outputStream, "Build Passport")
        }

        println("Файл создан: ${propFile.absolutePath}")

        println("Номер сборки: $buildNumber")
    }
}

tasks.register<GenerateBuildPassportTask>("generateBuildPassport") {
    group = "Custom"
    description = "Генерирует build-passport.properties"
}
tasks.named("processResources") {
    dependsOn(tasks.named("generateBuildPassport"))
}