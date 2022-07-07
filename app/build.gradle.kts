import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("org.jetbrains.compose")
    id("org.openjfx.javafxplugin")
}

javafx {
    version = "17.0.1"
    modules = listOf("javafx.web", "javafx.swing", "javafx.controls")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":quickbooks-api"))

    implementation(Dependencies.featherIcons)

    implementation(Dependencies.h2Database)

    implementation(Dependencies.exposedCore)
    implementation(Dependencies.exposedDao)
    implementation(Dependencies.exposedJdbc)
    implementation(Dependencies.exposedJodaTime)

    testImplementation(Dependencies.coroutinesTest)
}

tasks.register("prepareKotlinBuildScriptModel") {
    // https://youtrack.jetbrains.com/issue/KTIJ-16480
}

compose.desktop {
    application {
        mainClass = "tech.codeabsolute.MainKt"
        nativeDistributions {
            modules("java.instrument", "java.naming", "java.net.http", "java.prefs", "java.security.jgss", "java.sql", "jdk.jfr", "jdk.jsobject", "jdk.unsupported", "jdk.unsupported.desktop", "jdk.xml.dom")
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe)
            packageName = "Sang-T MP"
            packageVersion = "1.0.0"
        }
    }
}
