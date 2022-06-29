import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("org.jetbrains.compose")
    id("org.openjfx.javafxplugin")
}

javafx {
    version = "16"
    modules = listOf("javafx.web", "javafx.swing")
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
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi)
            packageName = "app"
            packageVersion = "1.0.0"
        }
    }
}
