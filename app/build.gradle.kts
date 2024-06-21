plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    namespace = "com.capstone.pulih"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.capstone.pulih"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String","BASE_URL", "\"https://pulih-apps-cc-tpfmfjmgvq-et.a.run.app/api/\"")
    }
    buildFeatures{
        viewBinding = true
        buildConfig = true
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
}

dependencies {
//    Activity, Fragment dan Material
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.ktx)

//    Navigation
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

//    FirebaseBOM, Firebase Analytics dan FirebaseAuth
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)

//    Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)


//    ViewModel
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
// LiveData
    implementation (libs.androidx.lifecycle.livedata.ktx)

//    Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.datastore.preferences.core.jvm)
    testImplementation(libs.kotlinx.coroutines.test)

//    Okhttp3
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

//    Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}