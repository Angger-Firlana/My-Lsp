plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.mylsp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mylsp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.foundation)
    val camerax_version = "1.3.0"

    // CameraX
    implementation("androidx.camera:camera-core:$camerax_version")
    implementation("androidx.camera:camera-camera2:$camerax_version")
    implementation("androidx.camera:camera-lifecycle:$camerax_version")
    implementation("androidx.camera:camera-view:$camerax_version")

    // Compose Material & Material 3
    implementation("androidx.compose.material:material:1.5.4")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // ⭐ Material3 - GUNAKAN VERSI EKSPLISIT untuk Pull-to-Refresh
    implementation("androidx.compose.material3:material3:1.3.0")

    // ⭐ TAMBAHKAN INI - Lifecycle & ViewModel untuk Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // ML Kit Barcode
    implementation("com.google.mlkit:barcode-scanning:17.2.0")

    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // ⭐ TAMBAHKAN INI - OkHttp Logging Interceptor (untuk debugging API)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Security & DataStore
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // ZXing (QR Code)
    implementation("com.google.zxing:core:3.5.3")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")

    // Firebase (jika diperlukan)
    implementation(libs.firebase.crashlytics.buildtools)

    // Material Icons
    implementation("androidx.compose.material:material-icons-core:1.6.8")
    implementation("androidx.compose.material:material-icons-extended:1.6.8")

    // ⭐ TAMBAHKAN INI - Coil untuk Image Loading (optional tapi recommended)
    implementation("io.coil-kt:coil-compose:2.5.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}