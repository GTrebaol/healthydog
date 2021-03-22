object Versions {

    object Gradle {
        const val GRADLE_VERSION = "4.0.1"
    }

    object Kotlin {
        const val KOTLIN_VERSION = "1.4.21"
    }

    object Android {
        const val ANDROIX_FRAGMENT = "1.3.0"
        const val LIFECYCLE = "2.2.0"
        const val ANDROIDX_CORE = "1.3.2"
        const val DATABINDING = "3.5.0"
        const val DATABINDING_RUNTIME = "4.1.2"
        const val APP_COMPAT = "1.2.0-beta01"
        const val SECURITY_CRYPTO = "1.0.0-rc03"
    }

    object EventBus {
        const val LOCAL_BROADCAST_MANAGER = "1.0.0"
    }

    object Asynchronous {
        const val COROUTINES = "1.4.1"
    }

    object DependencyInjection {
        const val KOIN = "2.1.6"
    }

    object Testing {
        const val CORE_TESTING = "2.1.0"
        const val JUNIT5 = "5.7.0"
        const val MOCKK = "1.10.0"
        const val COROUTINES = Asynchronous.COROUTINES
    }

    object Network {
        const val MOSHI = "1.11.0"
        const val CONVERTER_MOSHI = "2.9.0"
        const val OKTTP = "4.9.0"
        const val RETROFIT = "2.6.2"
    }

    object Api {
        const val JSON_MOSHI_KOTLIN = "1.9.2"
        const val JSON_MOSHI_ADAPTER = "1.9.2"
        const val THREETENBP_ADAPTER = "1.4.0"
    }

    object Database {
        const val ROOM = "2.2.6"
    }

    object UI {
        const val CONSTRAINT_LAYOUT = "2.0.0-beta6"
        const val LOTTIE = "3.0.0"
        const val CHART = "3.1.0"
        const val GOOGLE_PLACES = "2.4.0"
        const val GOOGLE_PLAY_CORE = "1.8.1"

    }

    object Logging {
        const val TIMBER = "4.7.1"
    }

    object Monitoring {
        // When using the BoM, you don't specify versions in Firebase library dependencies
        const val FIREBASE_BOM = "26.0.0"

        // Override dependencies to match with BoM 26.0.0 - libs used in Anrlib
        const val FIREBASE_ML_VISION = "24.1.0"
        const val GOOGLE_SERVICES_VISON = "20.1.0"
    }
}