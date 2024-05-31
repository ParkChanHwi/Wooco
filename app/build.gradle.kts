import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {

    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services") // Google services Gradle plugin 추가
}

android {
    namespace = "com.odal.wooco"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.odal.wooco"
        minSdk = 23 // 변경된 부분
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }



    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    //implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    // Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-database-ktx:20.0.4")
    implementation("com.google.firebase:firebase-auth-ktx")

    // Import for calendar 5/22 추가한 내용
    implementation("com.prolificinteractive:material-calendarview:1.4.3")
    implementation("com.google.android.material:material:1.5.0")

    //bottom sheet
    implementation ("com.android.support:design:28.0.0")
    implementation ("com.google.android.material:material:1.2.0-alpha01")
}