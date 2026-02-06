plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.coverme"
    compileSdk {
        version = release(36)
    }

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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.10.0")

    // ViewModel (often used with LiveData)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")

    //fragment ktx
    implementation("androidx.fragment:fragment-ktx:1.8.9")

    //data store
    implementation("androidx.datastore:datastore-preferences:1.2.0")

    // Room runtime
    implementation ("androidx.room:room-runtime:2.6.1")

    // Kotlin extensions (Coroutines support)
    implementation ("androidx.room:room-ktx:2.6.1")

    // Ksp for room
    ksp("androidx.room:room-compiler:2.6.1")

    //ksp for hilt
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")

    implementation("com.google.dagger:hilt-android:2.57.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Core Moshi library
    implementation("com.squareup.moshi:moshi:1.15.0")

    // Kotlin utilities (includes KotlinJsonAdapterFactory if needed for runtime reflection)
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

    implementation ("com.squareup.retrofit2:converter-moshi:2.4.0")

    // Compile-time code generation (recommended)
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
}