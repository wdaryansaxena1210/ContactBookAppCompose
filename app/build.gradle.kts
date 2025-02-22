plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("io.realm.kotlin")
//    id("io.realm.kotlin")
}

android {
    namespace = "com.example.contactbookappcompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.contactbookappcompose"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
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
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation ("io.realm.kotlin:library-base:2.0.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")

//    implementation ("io.realm.kotlin:library-base:1.16.0")
//    // If using Device Sync
//    implementation ("io.realm.kotlin:library-sync:1.16.0")
//    // If using coroutines with the SDK
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0")
//
//    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")


}