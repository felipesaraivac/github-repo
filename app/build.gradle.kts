plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.saraiva.github"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.saraiva.github"
        minSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose=true
    }

    packaging {
        resources {
            merges += "META-INF/**"
        }
    }

}

dependencies {

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.adapter.rxjava2)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.material.iconsExtended)

    implementation(libs.androidx.paging.runtime)

    implementation(libs.androidx.paging.compose)

    implementation(libs.coil.compose)

    implementation(libs.coil.network.okhttp)

    implementation(libs.hilt.android)

    ksp (libs.hilt.android.compiler)

    implementation (libs.androidx.room)
    ksp( libs.androidx.room.compiler)
    debugImplementation(libs.androidx.ui.tooling)

    implementation(libs.androidx.test.core)
    implementation(libs.test.mockk)
    implementation(libs.test.kotlinCoroutines)
    implementation(libs.test.junit)
    testImplementation(libs.test.kotest)
    implementation(libs.androidx.test.espresso.core)
    implementation(libs.androidx.test.ext)
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.rules)
}