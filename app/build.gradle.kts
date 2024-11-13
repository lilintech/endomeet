plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.endo_ai"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.endo_ai"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.compose.material:material-icons-core:1.2.0")
    implementation("androidx.compose.material:material-icons-extended:1.2.0")


    implementation("androidx.navigation:navigation-compose:2.8.2")

    implementation("androidx.compose.material:material:1.7.3")

    // Dagger  -Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation(libs.androidx.hilt.navigation.compose)
    implementation("androidx.compose.runtime:runtime-livedata:1.7.3")

    // Retrofit && KotlinX Serialization
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")

    implementation("androidx.compose.animation:animation:1.5.0")
    implementation("androidx.compose.material:material:1.5.0")

    //accompanist-compose
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.18.0")
    implementation("com.google.accompanist:accompanist-pager:0.18.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.18.0")

    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-auth-ktx:21.1.0")

    implementation("com.google.firebase:firebase-firestore-ktx:24.9.1")


}