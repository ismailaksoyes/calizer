plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = buildConfigVersions.compileSdkVersion
    buildToolsVersion = buildConfigVersions.buildToolsVersion
    defaultConfig {
        applicationId = "com.avalon.calizer"
        minSdk = buildConfigVersions.minSdkVersion
        targetSdk = buildConfigVersions.targetSdkVersion
        versionCode = buildConfigVersions.versionCode
        versionName = buildConfigVersions.versionName
        buildFeatures.dataBinding
        buildFeatures.viewBinding
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas".toString())
            }
        }
    }
    lint {
        isCheckDependencies = true
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
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
    implementation(androidxsupportDependencies.core_ktx)
    implementation(androidxsupportDependencies.appcompat)
    implementation(androidxsupportDependencies.material)
    implementation(androidxsupportDependencies.preference)
    implementation(androidxsupportDependencies.constraintlayout)
    implementation(androidxsupportDependencies.multidex)
    implementation(androidxsupportDependencies.legacy_support)
    //Kotlin components
    implementation(kotlinDependencies.kotlin)
   // implementation(kotlinDependencies.coroutines_core)
    implementation(kotlinDependencies.coroutines)
    //retrofit
    implementation(retrofitDependencies.retrofit2)
    implementation(retrofitDependencies.converter_gson)
    implementation(retrofitDependencies.adapter_rxjava)
    implementation(retrofitDependencies.logging_interceptor)
    // Room components
    implementation(roomDependencies.room_ktx)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(roomDependencies.room_compiler)
    testImplementation(roomDependencies.room_testing)
    //Lifecycle
    implementation(androidxsupportDependencies.lifecycle_viewmodel)
    implementation(androidxsupportDependencies.lifecycle_runtime)

    //Testing
    testImplementation(testingDepencies.junit)
    testImplementation(testingDepencies.junit_ext)
    testImplementation(testingDepencies.espresso)

    //glide
    implementation(glideDependencies.glide)
    kapt(glideDependencies.glide_compiler)


    implementation(mlkitDependencies.mlkit_face)
    implementation(mlkitDependencies.mlkit_pose)

    //Dagger - Hilt
    implementation(hiltDependencies.hilt)
    kapt(hiltDependencies.hilt_compiler)

    //Fb Shimmer Animation
    implementation(shimmerDependencies.shimmer)


    // Activity KTX for viewModels()
    implementation(androidxsupportDependencies.activity_ktx)
    implementation(androidxsupportDependencies.fragment_ktx)

    //navigation comp
    implementation(androidxsupportDependencies.navigation_fragment)
    implementation(androidxsupportDependencies.navigation_ui)
    implementation(androidxsupportDependencies.navigation_runtime)

    implementation(lottieDependencies.lottie)



    implementation("com.github.tapadoo:alerter:7.2.4")


}