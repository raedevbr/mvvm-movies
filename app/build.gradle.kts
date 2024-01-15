import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.raedevbr.movies"
    compileSdk = 34

    val appPropertiesFile by extra(file("app.properties"))
    val appProperties by extra { Properties().apply { load(appPropertiesFile.reader()) } }

    defaultConfig {
        applicationId = "com.raedevbr.movies"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "com.raedevbr.movies.CustomTestRunner"
        buildConfigField("String", "API_KEY", "\"${appProperties["API_KEY"]}\"")
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
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
        buildConfig = true
        viewBinding = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    sourceSets {
        val sharedTestDir = "src/sharedTest/resources"
        getByName("test") {
            resources.srcDirs(sharedTestDir)
        }
        getByName("androidTest") {
            resources.srcDirs(sharedTestDir)
        }
    }

}

dependencies {
    // Core
    implementation(libs.core.ktx)
    implementation(libs.kotlin.stdlib)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.annotation)
    implementation(libs.kotlinx.coroutines.android)

    // Arch Components
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Navigation Component
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Dagger Hilt for DI
    implementation(libs.hilt.android.core)
    kapt(libs.hilt.compiler)

    // Retrofit for network requests
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp3.logging.interceptor)

    // Image loading
    implementation(libs.picasso)

    // Serializable and deserializable
    implementation(libs.gson)

    /**-------------------------testing libs-------------------------**/
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.compiler)
    kaptAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.espresso.idling.resource)
    implementation(libs.espresso.contrib)
    implementation(libs.arch.core.testing)
    implementation(libs.kotlinx.coroutines.test)
}