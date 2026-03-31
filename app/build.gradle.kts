plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.coverme"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.coverme"
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.paging.runtime.ktx)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // ViewModel (often used with LiveData)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //fragment ktx
    implementation(libs.androidx.fragment.ktx)

    //data store
    implementation(libs.androidx.datastore.preferences)

    // Room runtime
    implementation (libs.androidx.room.runtime)

    // Kotlin extensions (Coroutines support)
    implementation (libs.androidx.room.ktx)

    // Ksp for room
    ksp(libs.androidx.room.compiler)

    //ksp for hilt
    ksp(libs.hilt.android.compiler)

    implementation(libs.hilt.android)

    implementation(libs.retrofit)

    // Core Moshi library
    implementation(libs.moshi)

    // Kotlin utilities (includes KotlinJsonAdapterFactory if needed for runtime reflection)
    implementation(libs.squareup.moshi.kotlin)

    implementation (libs.converter.moshi)

    // Compile-time code generation (recommended)
    ksp(libs.moshi.kotlin.codegen)

    //glide
    implementation (libs.glide)

    //paging
    implementation(libs.androidx.paging.runtime.ktx)
}