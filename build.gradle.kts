// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
//    id("io.realm.kotlin") version "1.16.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("io.realm.kotlin") version "2.0.0" apply false
}