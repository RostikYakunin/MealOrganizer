plugins {
    id("com.android.application")
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android") version "2.44" apply false
    kotlin("android") version "1.9.0"
}

android {
    namespace = "com.example.mealorganizer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mealorganizer"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "0.01"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //Compose BOM and general
    implementation(platform(libs.androidx.compose.bom.v20240600))
    implementation(libs.ui)
    implementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.material.icons.extended)

    //ViewModel and navigation
    implementation(libs.activity.compose)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.navigation.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.core.ktx)
    implementation(libs.androidx.ui.text.google.fonts)
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation(libs.androidx.room.ktx)

    //Json serialization
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Coil
    implementation(libs.coil.compose)

    // Datastore Preferences Proto
    implementation(libs.datastore.preferences)
    implementation(libs.datastore)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(platform(libs.androidx.compose.bom.v20240100))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}
