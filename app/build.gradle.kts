plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(AppConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(AppConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
        applicationId = "com.gtreb.healthydog"
        minSdkVersion(AppConfig.MIN_SDK_VERSION)
        targetSdkVersion(AppConfig.TARGET_SDK_VERSION)
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = AppConfig.TEST_INSTRUMENTATION_RUNNER
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

    flavorDimensions(AppConfig.DIMENSION)
    productFlavors {
        create("production") {
            dimension = AppConfig.DIMENSION
        }
        create("staging") {
            applicationIdSuffix = ".staging"
            dimension = AppConfig.DIMENSION
        }

    }


    viewBinding {
        android.buildFeatures.dataBinding = true
        android.buildFeatures.viewBinding = true
    }

    packagingOptions {
        exclude("META-INF/notice.txt")
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
    //std lib
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //app libs
    implementation(AppDependencies.Android.APP_COMPAT)
    implementation(AppDependencies.Android.ANDROIDX_CORE)
    implementation(AppDependencies.Android.DATABINDING)
    implementation(AppDependencies.Android.SECURITY_CRYPTO)
    implementation(AppDependencies.Android.DATABINDING_RUNTIME)
    implementation(AppDependencies.Android.LIFECYCLE_LIVEDATA)
    implementation(AppDependencies.Android.LIFECYCLE_VIEWMODEL)


    implementation(AppDependencies.Kotlin.KOTLIN_STDLIB_JDK_7)
    implementation(AppDependencies.EventBus.LOCAL_BROADCAST_MANAGER)


    implementation(AppDependencies.DependencyInjection.KOIN_CORE)
    implementation(AppDependencies.DependencyInjection.KOIN_ANDROID)
    implementation(AppDependencies.DependencyInjection.KOIN_ANDROID_FRAGMENT)
    implementation(AppDependencies.DependencyInjection.KOIN_ANDROID_VIEWMODEL)
    implementation(AppDependencies.DependencyInjection.KOIN_ANDROID_EXT)
    implementation(AppDependencies.DependencyInjection.KOIN_ANDROID_SCOPE)


    implementation(AppDependencies.UI.CONSTRAINT_LAYOUT)
    implementation(AppDependencies.UI.LOTTIE)


    implementation(AppDependencies.Asynchronous.COROUTINES_CORE)
    implementation(AppDependencies.Asynchronous.COROUTINES_ANDROID)

    implementation(AppDependencies.Logging.TIMBER)


    //test libs
    testImplementation(AppDependencies.Testing.JUNIT5)
}