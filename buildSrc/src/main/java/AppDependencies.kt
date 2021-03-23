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
            "androidx.fragment:fragment-ktx:${Versions.Android.ANDROIX_FRAGMENT}"
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
        const val CONSTRAINT_LAYOUT =
            "androidx.constraintlayout:constraintlayout:${Versions.UI.CONSTRAINT_LAYOUT}"
        const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Versions.UI.RECYCLER_VIEW}"
        const val SUPPORT_DESIGN = "com.android.support:design:${Versions.UI.SUPPORT_DESIGN}"


        const val LOTTIE = "com.airbnb.android:lottie:${Versions.UI.LOTTIE}"
        const val GRAPH = "com.github.PhilJay:MPAndroidChart:${Versions.UI.CHART}"
        const val GOOGLE_PLACES =
            "com.google.android.libraries.places:places:${Versions.UI.GOOGLE_PLACES}"
        const val GOOGLE_PLAY_CORE =
            "com.google.android.play:core-ktx:${Versions.UI.GOOGLE_PLAY_CORE}"

        const val ROUNDEDIMAGEVIEW =
            "com.rishabhharit.roundedimageview:RoundedImageView:${Versions.UI.ROUNDEDIMAGEVIEW}"

    }


    object Network {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.Network.RETROFIT}"
        const val CONVERTER_MOSHI =
            "com.squareup.retrofit2:converter-moshi:${Versions.Network.RETROFIT}"
        const val CONVERTER_SCALARS =
            "com.squareup.retrofit2:converter-scalars:${Versions.Network.RETROFIT}"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.Network.OKTTP}"
        const val OKHTTP_INTERCEPTOR =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OKTTP}"
        const val MOSHI = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.Network.MOSHI}"
    }

    object Api {
        const val JSON_MOSHI_KOTLIN =
            "com.squareup.moshi:moshi-kotlin:${Versions.Api.JSON_MOSHI_KOTLIN}"
        const val JSON_MOSHI_ADAPTER =
            "com.squareup.moshi:moshi-adapters:${Versions.Api.JSON_MOSHI_ADAPTER}"
    }

    object Database {
        const val ROOM = "androidx.room:room-ktx:${Versions.Database.ROOM}"
        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.Database.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.Database.ROOM}"
    }


    object Testing {
        const val CORE = "androidx.arch.core:core-runtime:${Versions.Testing.CORE_TESTING}"
        const val JUNIT5 = "org.junit.jupiter:junit-jupiter:${Versions.Testing.JUNIT5}"
        const val MOCKK = "io.mockk:mockk:${Versions.Testing.MOCKK}"
        const val COROUTINES =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Testing.COROUTINES}"

    }

    object EventBus {
        const val LOCAL_BROADCAST_MANAGER =
            "androidx.localbroadcastmanager:localbroadcastmanager:${Versions.EventBus.LOCAL_BROADCAST_MANAGER}"
    }

    object Logging {
        const val TIMBER = "com.jakewharton.timber:timber:${Versions.Logging.TIMBER}"
    }
}