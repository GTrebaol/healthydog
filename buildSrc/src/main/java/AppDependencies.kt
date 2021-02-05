import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {

    object Kotlin {
        const val KOTLIN_STDLIB_JDK_8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.Kotlin.KOTLIN_VERSION}"
        const val KOTLIN_STDLIB_JDK_7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Kotlin.KOTLIN_VERSION}"
        const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:${Versions.Kotlin.KOTLIN_VERSION}"
        const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Kotlin.KOTLIN_VERSION}"
        const val KOTLIN_STDLIB_COMMON = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.Kotlin.KOTLIN_VERSION}"
        const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Kotlin.KOTLIN_VERSION}"
    }

    object DependencyInjection {
        const val KOIN_CORE = "org.koin:koin-core:${Versions.DependencyInjection.KOIN}"
        const val KOIN_ANDROID = "org.koin:koin-android:${Versions.DependencyInjection.KOIN}"
        const val KOIN_ANDROID_SCOPE = "org.koin:koin-android-scope:${Versions.DependencyInjection.KOIN}"
        const val KOIN_ANDROID_VIEWMODEL = "org.koin:koin-android-viewmodel:${Versions.DependencyInjection.KOIN}"
        const val KOIN_ANDROID_EXT = "org.koin:koin-android-ext:${Versions.DependencyInjection.KOIN}"
        const val KOIN_ANDROID_FRAGMENT = "org.koin:koin-androidx-fragment:${Versions.DependencyInjection.KOIN}"
    }

    object Android {
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.Android.APP_COMPAT}"
        const val LIFECYCLE = "androidx.lifecycle:lifecycle-extensions:${Versions.Android.LIFECYCLE}"
        const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Android.LIFECYCLE}"
        const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Android.LIFECYCLE}"
        const val FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.Android.FRAGMENT}"
        const val TOOLS_BUILD_GRADLE = "com.android.tools.build:gradle:${Versions.Android.TOOLS_BUILD_GRADLE}"
        const val ANDROID_MATERIAL = "com.google.android.material:material:${Versions.Android.ANDROID_MATERIAL}"
        const val RXANDROID = "io.reactivex.rxjava2:rxandroid:${Versions.Android.RXANDROID}"
        const val RXJAVA = "io.reactivex.rxjava2:rxjava:${Versions.Android.RXJAVA}"

        // contains encrypted sharedPreferences
        const val SECURITY_CRYPTO = "androidx.security:security-crypto:${Versions.Android.SECURITY_CRYPTO}"

        // Databinding compiler
        const val DATABINDING = "com.android.databinding:compiler:${Versions.Android.DATABINDING}"

        const val DYNAMIC_ANIMATION = "androidx.dynamicanimation:dynamicanimation:${Versions.Android.DYNAMIC_ANIMATION}"
    }

    object Asynchronous {
        const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Asynchronous.COROUTINES}"
        const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Asynchronous.COROUTINES}"
    }

    object Api {
        const val JSON_MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:${Versions.Api.JSON_MOSHI_KOTLIN}"
        const val JSON_MOSHI_ADAPTER = "com.squareup.moshi:moshi-adapters:${Versions.Api.JSON_MOSHI_ADAPTER}"
        const val THREETENBP_ADAPTER = "org.threeten:threetenbp:${Versions.Api.THREETENBP_ADAPTER}"
    }

    object Network {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.Network.RETROFIT}"
        const val RETROFIT_CONVERTER_JSON = "com.squareup.retrofit2:converter-moshi:${Versions.Network.RETROFIT}"
        const val RETROFIT_CONVERTER_SCALARS = "com.squareup.retrofit2:converter-scalars:${Versions.Network.RETROFIT}"
        const val HTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.Network.LOGGING_INTERCEPTOR}"
    }


    object Testing {
        const val CORE = "androidx.arch.core:core-runtime:${Versions.Testing.CORE_TESTING}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.Testing.ESPRESSO}"
        const val JACOCO = "org.jacoco:org.jacoco.core:${Versions.Testing.JACOCO}"
        const val JUNIT5 = "org.junit.jupiter:junit-jupiter:${Versions.Testing.JUNIT5}"
        const val JUNIT5_PARAMS = "org.junit.jupiter:junit-jupiter-params:${Versions.Testing.JUNIT5}"

        const val MOCKK = "io.mockk:mockk:${Versions.Testing.MOCKK}"
        const val TRUTH = "com.google.truth:truth:${Versions.Testing.GOOGLE_TRUTH}"
        const val SONARQUBE = "org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:${Versions.Testing.SONARQUBE}"
        const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Testing.COROUTINES}"
    }


    object EventBus {
        const val LOCAL_BROADCAST_MANAGER = "androidx.localbroadcastmanager:localbroadcastmanager:${Versions.EventBus.LOCAL_BROADCAST_MANAGER}"
    }

    object Logging {
        const val TIMBER = "com.jakewharton.timber:timber:${Versions.Logging.TIMBER}"
    }

    object Monitoring {
        // When using the BoM, you don't specify versions in Firebase library dependencies
        const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Versions.Monitoring.FIREBASE_BOM}"
        const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
        const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics-ktx"

        // Override dependencies to match with BoM 26.0.0 - libs used in Anrlib
        const val FIREBASE_ML_VISION = "com.google.firebase:firebase-ml-vision:${Versions.Monitoring.FIREBASE_ML_VISION}"
        const val GOOGLE_SERVICES_VISON = "com.google.android.gms:play-services-vision:${Versions.Monitoring.GOOGLE_SERVICES_VISON}"
    }

    object Security {
        const val ROOTBEER = "com.scottyab:rootbeer-lib:${Versions.Security.ROOTBEER}"
    }
}