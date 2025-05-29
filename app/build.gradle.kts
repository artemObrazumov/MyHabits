plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.artem_obrazumov.habits"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.artem_obrazumov.habits"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
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

    implementation(platform(libs.compose.bom))

    implementation(libs.bundles.core)
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.tooling)
    implementation(libs.bundles.serialization)
    implementation(libs.bundles.hilt)
    implementation(libs.bundles.work)
    implementation(libs.bundles.room)
    implementation(libs.bundles.datastore)
    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.navigation)

    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.bundles.testing.compose)

    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.room.compiler)
    kapt(libs.hilt.compiler)
    kapt(libs.work.hilt.compiler)
}