plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

group = "tech.codeabsolute"
version = "0.1.0"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "com.google.devtools.ksp")

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    sourceSets {
        val main by getting {
            java.srcDirs("${buildDir}/generated/ksp/$name/kotlin")
        }
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(Dependencies.kotlinCoroutines)
        implementation(Dependencies.swingCoroutines)
        implementation(Dependencies.javaFxCoroutines)
        implementation(Dependencies.koinCore)
        implementation(Dependencies.koinAnnotations)
        ksp(Dependencies.koinKsp)

        // https://www.slf4j.org/codes.html#StaticLoggerBinder
        implementation(Dependencies.slf4j)

        testImplementation(Dependencies.junitApi)
        testImplementation(Dependencies.junitParams)
        testRuntimeOnly(Dependencies.junitEngine)
        testImplementation(Dependencies.mockk)
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}
