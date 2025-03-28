import org.gradle.kotlin.dsl.libs
import org.jetbrains.kotlin.commonizer.OptimisticNumberCommonizationEnabledKey.alias

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.noteapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.noteapp"
        minSdk = 26
        targetSdk = 34
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
    // Core KTX
    implementation(libs.androidx.core.ktx)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Lifecycle & ViewModel
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Activity Compose
    implementation(libs.androidx.activity.compose)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // LiveData support for Compose
    implementation(libs.androidx.compose.runtime.livedata)

    // Coroutine
    implementation(libs.kotlinx.coroutines.core)

    // Room database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // GSON
    implementation("com.google.code.gson:gson:2.10.1")

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Hilt
    implementation(libs.hilt)
    kapt(libs.hiltcompiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //LazyVerticalGrid
    implementation ("androidx.compose.foundation:foundation:1.4.0")
    implementation ("androidx.compose.foundation:foundation-layout:1.4.0")





    //workmanager
    implementation ("androidx.work:work-runtime:2.10.0")

    //constraintlayout
    implementation("androidx.constraintlayout:constraintlayout-compose-android:1.1.1")



    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}