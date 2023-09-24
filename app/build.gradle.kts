plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.showcase.moviewave"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.showcase.moviewave"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = "moviewave"
            keyPassword = "dzuw3FR57e3e3EATaefruPu2rECtHd7E"
            storeFile = file("keys/release.jks")
            storePassword = "fat7Raq24uwEGr2Weu8ubarewEhu5aya"
        }

        getByName("debug") {
            keyAlias = "moviewave"
            keyPassword = "dzuw3FR57e3e3EATaefruPu2rECtHd7E"
            storeFile = file("keys/release.jks")
            storePassword = "fat7Raq24uwEGr2Weu8ubarewEhu5aya"
        }
    }

    flavorDimensions.add("version")
    productFlavors {
        create("production") {
            applicationId = "com.showcase.moviewave"
            versionCode = 2
            versionName = "1.1"
        }
        create("dev") {
            applicationId = "com.showcase.moviewave"
            versionCode = 1
            versionName = "1.0"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            multiDexEnabled = true
            isDebuggable = false

            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            firebaseAppDistribution {
                artifactType = "APK"
//                releaseNotes = "Release notes"
                releaseNotesFile = "releasenotes/version-1.1(2).txt"
                testers = "harshalisachani@gmail.com"
            }
        }

        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true

            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

//                applicationVariants.all { variant ->
//                val flavor = variant.productFlavors[0].name
//                val version = variant.versionName
//                outputFileName = "MovieWave-${flavor}-v${version}.apk"
//            }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //kotlin extension helper
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // gson
    implementation("com.google.code.gson:gson:2.10.1")

    // Koin for Kotlin apps
    implementation("io.insert-koin:koin-core:3.1.5")
    // Koin main features for Android (Scope,ViewModel ...)
    implementation("io.insert-koin:koin-android:3.1.5")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.10.0")

    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
}