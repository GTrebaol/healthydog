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
        manifestPlaceholders["API_KEY"] = "YOUR_API_KEY"
        buildConfigField("String", "API_KEY", "\"YOUR_API_KEY\"")
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
        create("staging") {
            applicationIdSuffix = ".staging"
            dimension = AppConfig.DIMENSION
        }
        create("production") {
            dimension = AppConfig.DIMENSION
        }

    }


    viewBinding {
        android.buildFeatures.dataBinding = true
        android.buildFeatures.viewBinding = true
    }

    packagingOptions {
        exclude("META-INF/notice.txt")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/NOTICE")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/gradle/incremental.annotation.processors")
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
    implementation(AppDependencies.Android.ANDROIDX_FRAGMENT)
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

    implementation(AppDependencies.Network.RETROFIT)
    implementation(AppDependencies.Network.CONVERTER_MOSHI)
    implementation(AppDependencies.Network.MOSHI)
    implementation(AppDependencies.Network.OKHTTP)
    implementation(AppDependencies.Network.OKHTTP_INTERCEPTOR)
    implementation(AppDependencies.Network.CONVERTER_SCALARS)


    implementation(AppDependencies.Api.JSON_MOSHI_KOTLIN)
    implementation(AppDependencies.Api.JSON_MOSHI_ADAPTER)

    implementation(AppDependencies.UI.CONSTRAINT_LAYOUT)
    implementation(AppDependencies.UI.LOTTIE)
    implementation(AppDependencies.UI.GRAPH)
    implementation(AppDependencies.UI.GOOGLE_PLACES)
    implementation(AppDependencies.UI.GOOGLE_PLAY_CORE)


    implementation(AppDependencies.Asynchronous.COROUTINES_CORE)
    implementation(AppDependencies.Asynchronous.COROUTINES_ANDROID)

    implementation(AppDependencies.Logging.TIMBER)


    //test libs
    testImplementation(AppDependencies.Testing.JUNIT5)
    testImplementation(AppDependencies.Testing.CORE)
    testImplementation(AppDependencies.Testing.COROUTINES)
    testImplementation(AppDependencies.Testing.MOCKK)
}