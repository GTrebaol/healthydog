object AppDependencies {

    object Kotlin {
        const val KOTLIN_STDLIB_JDK_7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Kotlin.KOTLIN_VERSION}"
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
        const val ANDROIDX_CORE = "androidx.core:core-ktx:${Versions.Android.ANDROIDX_CORE}"
        const val ANDROIDX_FRAGMENT =
            "androidx.fragment:fragment:${Versions.Android.ANDROIX_FRAGMENT}"
        const val LIFECYCLE_LIVEDATA =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Android.LIFECYCLE}"
        const val LIFECYCLE_VIEWMODEL =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Android.LIFECYCLE}"

        // contains encrypted sharedPreferences
        const val SECURITY_CRYPTO =
            "androidx.security:security-crypto:${Versions.Android.SECURITY_CRYPTO}"

        // Databinding compiler
        const val DATABINDING = "com.android.databinding:compiler:${Versions.Android.DATABINDING}"
        const val DATABINDING_RUNTIME =
            "androidx.databinding:databinding-runtime:${Versions.Android.DATABINDING_RUNTIME}"

    }

    object Asynchronous {
        const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Asynchronous.COROUTINES}"
        const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Asynchronous.COROUTINES}"
    }

    object UI {
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.UI.CONSTRAINT_LAYOUT}"
        const val LOTTIE = "com.airbnb.android:lottie:${Versions.UI.LOTTIE}"
        const val GRAPH = "com.github.PhilJay:MPAndroidChart:${Versions.UI.CHART}"
        const val GOOGLE_PLACES = "com.google.android.libraries.places:places:${Versions.UI.GOOGLE_PLACES}"
    }


    object Network {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.Network.RETROFIT}"
        const val CONVERTER_MOSHI =
            "com.squareup.retrofit2:converter-moshi:${Versions.Network.CONVERTER_MOSHI}"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.Network.OKTTP}"
        const val OKHTTP_INTERCEPTOR =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OKTTP}"
        const val MOSHI = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.Network.MOSHI}"
    }



    object Testing {
        const val JUNIT5 = "org.junit.jupiter:junit-jupiter:${Versions.Testing.JUNIT5}"
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
}