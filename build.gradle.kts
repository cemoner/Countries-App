buildscript {
    val kotlin_version = "1.8.22"
    dependencies {
        classpath ("com.android.tools.build:gradle:8.1.0")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
    }
}



// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}