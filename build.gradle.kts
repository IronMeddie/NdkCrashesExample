// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    id("io.appmetrica.analytics") version "1.0.1" apply false

}

val API_KEY = getStringProperty("API_KEY")

fun getStringProperty(key: String): String {
    val properties = java.util.Properties().apply {
        load(project.rootProject.file("local.properties").inputStream())
    }
    return properties.getProperty(key) ?: error("Property $key not found")
}
