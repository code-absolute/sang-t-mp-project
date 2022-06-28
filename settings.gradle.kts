pluginManagement {
    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        id("org.openjfx.javafxplugin").version(extra["javafx.version"] as String)
        id("com.google.devtools.ksp").version(extra["koin.ksp"] as String)
    }
}

rootProject.name = "sang-t-mp-project"
include("app")
include("quickbooks-api")
