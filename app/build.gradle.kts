plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("com.google.devtools.ksp")
    id("org.jetbrains.dokka") version "1.9.10"
}

android {
    namespace = "com.example.visionapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.visionapplication"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders.putAll(
            mutableMapOf(
                "auth0Domain" to "@string/com_auth0_domain",
                "auth0Scheme" to "@string/com_auth0_scheme"
            )
        )


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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        // Enable support for the new language APIs


    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        //viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}

dependencies {
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    val nav_version = "2.7.5"

    // Java language implementation

    implementation("androidx.core:core-ktx:1.12.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    implementation("androidx.activity:activity-compose:1.8.2")

    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling:1.5.4")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // Jetpack Compose Integration
    implementation("androidx.navigation:navigation-compose:$nav_version")
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")



    // The compose calendar library
    implementation("com.kizitonwose.calendar:compose:2.4.0")

    //db
    implementation("com.microsoft.sqlserver:mssql-jdbc:9.2.1.jre11")
    implementation("io.coil-kt:coil-compose:2.5.0")


    implementation("androidx.compose.material3:material3-window-size-class")


    implementation("androidx.compose.foundation:foundation:1.5.4")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")


    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

    // Room
    val room_version = "2.5.0"
    //noinspection GradleDependency
    implementation("androidx.room:room-runtime:$room_version")
    //noinspection GradleDependency
    implementation("androidx.room:room-ktx:$room_version")
    //noinspection GradleDependency
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Test helpers
    //noinspection GradleDependency
    testImplementation("androidx.room:room-testing:$room_version")
    //noinspection GradleDependency
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // Auth0
    //noinspection GradleDynamicVersion
    implementation("com.auth0.android:auth0:+")
    //noinspection GradleDynamicVersion
    implementation("com.auth0.android:jwtdecode:+")
    implementation("io.coil-kt:coil-compose:2.5.0")

    dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:1.9.10")
}