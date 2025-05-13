import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("io.appmetrica.analytics")
}

android {
    namespace = "com.ironmeddie.myndkapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ironmeddie.myndkapplication"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    buildFeatures {
        compose = true
    }
    applicationVariants.configureEach {
        val variant = this
        val uploadSymbolsTask = project.tasks.findByName("upload${variant.name.capitalize()}AppMetricaNdkSymbols")
        if (uploadSymbolsTask != null) {
            variant.assembleProvider.configure { finalizedBy(uploadSymbolsTask) }                     // Если используете apk
//            project.tasks.named("bundle${variant.name.capitalize()}") { finalizedBy(uploadSymbolsTask) } // Если используете aab
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.analytics)
}

appmetrica {
    postApiKey = { applicationVariant ->
        getStringProperty("POST_API_KEY")
    }

    ndk {
        // Optional
        enable = { applicationVariant -> true }
        addNdkCrashesDependency = { applicationVariant -> true }      // Optional
    }
}


val localProperties = Properties().apply {
    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

fun getStringProperty(key: String): String {
    return localProperties.getProperty(key) ?: error("Property $key not found in local.properties")
}