object Versions {

    object Gradle {
        const val GRADLE_VERSION = "4.0.1"
    }

    object Kotlin {
        const val KOTLIN_VERSION = "1.4.21"
    }

    object Android {
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
        const val JUNIT5 = "5.7.0"

    }

    object UI {
        const val CONSTRAINT_LAYOUT = "2.0.0-beta6"
        const val LOTTIE = "3.0.0"
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