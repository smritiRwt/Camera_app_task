plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)          // Hilt DI plugin
    alias(libs.plugins.ksp)           // KSP for annotation processing (Hilt)
}

android {
    namespace = "com.example.cameraapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.multizoom.camera"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        jniLibs {
            useLegacyPackaging = true
        }
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
    // ── Core ──────────────────────────────────────────────────────────────────
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)

    // ── Jetpack Compose ───────────────────────────────────────────────────────
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.compose.runtime)
    debugImplementation(libs.androidx.ui.tooling)

    // ── Navigation ────────────────────────────────────────────────────────────
    implementation(libs.androidx.navigation.compose)

    // ── Hilt (Dependency Injection) ───────────────────────────────────────────
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // ── CameraX ───────────────────────────────────────────────────────────────
    implementation(libs.camerax.core)
    implementation(libs.camerax.camera2)    // Camera2 implementation
    implementation(libs.camerax.lifecycle)  // Lifecycle-aware camera
    implementation(libs.camerax.view)       // PreviewView composable helper
    implementation(libs.guava)              // Guava for ListenableFuture

    // ── Coroutines ────────────────────────────────────────────────────────────
    implementation(libs.kotlinx.coroutines.android)

    // ── Image Loading ─────────────────────────────────────────────────────────
    implementation(libs.coil.compose)

    // ── Permissions ───────────────────────────────────────────────────────────
    implementation(libs.accompanist.permissions)

    // ── Testing ───────────────────────────────────────────────────────────────
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}