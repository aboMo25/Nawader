plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.lastmusicapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lastmusicapp"
        minSdk = 21
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding=true
    }

}



dependencies {


    // AndroidX Core KTX
    implementation(libs.androidx.core.ktx)

    // AndroidX AppCompat
    implementation(libs.androidx.appcompat)

    // Material Components for Android
    implementation(libs.material)

    // AndroidX Activity
    implementation(libs.androidx.activity)

    // AndroidX ConstraintLayout
    implementation(libs.androidx.constraintlayout)
    implementation(libs.identity.credential)
    implementation(libs.firebase.firestore)
    implementation ("com.google.firebase:firebase-firestore")
    // JUnit testing framework
    testImplementation(libs.junit)

    // AndroidX JUnit testing extensions
    androidTestImplementation(libs.androidx.junit)

    // AndroidX Espresso core library for UI testing
    androidTestImplementation(libs.androidx.espresso.core)

    // Glide image loading and caching library
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation ("androidx.media3:media3-exoplayer:1.3.0")
    implementation ("androidx.media3:media3-ui:1.3.0")
    implementation ("androidx.media3:media3-common:1.3.0")
}
