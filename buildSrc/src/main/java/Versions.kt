object Versions {

    //libs
    const val coreKtx = "1.2.0"
    const val koin = "2.1.6"
    const val appcompat = "1.3.0-alpha01"
    const val constraintLayout = "2.0.0-beta8"

    //test
    const val junit = "4.12"
    const val extJunit = "1.1.1"
    const val espresso = "3.2.0"

    object Gradle {
        const val GRADLE_VERSION = "4.0.1"
    }


    object Kotlin {
        const val KOTLIN_VERSION = "1.4.21"
    }

    object Android {
        const val APP_COMPAT = "1.2.0-beta01"
        const val LIFECYCLE = "2.2.0"
        const val FRAGMENT = "1.2.5"
        const val TOOLS_BUILD_GRADLE = "3.6.3"
        const val ANDROID_MATERIAL = "1.0.0"
        const val DATABINDING = "3.5.0"
        const val RXANDROID = "3.0.0"
        const val RXJAVA = "3.0.4"

        // TODO: change with final version when available
        const val SECURITY_CRYPTO = "1.0.0-rc03"

        const val DYNAMIC_ANIMATION = "1.0.0"
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

    object Network {
        const val RETROFIT = "2.6.2" // 2.7+ minSdkVersion 26
        const val LOGGING_INTERCEPTOR = "4.9.0"
    }

    object Api {
        const val JSON_MOSHI_KOTLIN = "1.9.2"
        const val JSON_MOSHI_ADAPTER = "1.9.2"
        const val THREETENBP_ADAPTER = "1.4.0"
    }

    object Testing {
        const val CORE_TESTING = "2.1.0"
        const val ESPRESSO = "3.2.0-beta01"
        const val GOOGLE_TRUTH = "0.44"
        const val JACOCO = "0.8.5"
        const val JUNIT5 = "5.7.0"
        const val MOCKK = "1.10.0"
        const val SONARQUBE = "2.8"

        // Making sure test version is the same as app version.
        const val COROUTINES = Asynchronous.COROUTINES
    }

    object UI {
        const val CARD_VIEW = "1.0.0"
        const val CONSTRAINT_LAYOUT = "2.0.0-beta6"
        const val COIL = "0.13.0"
        const val RECYCLER_VIEW = "1.2.0-alpha03"
        const val SUPPORT_DESIGN = "28.0.0"
        const val FLEXBOX = "2.0.1"
        const val ROUNDEDIMAGEVIEW = "0.8.4"
        const val MATERIAL = "1.1.0"
        const val SWIPEREFRESHLAYOUT = "1.1.0"
        const val PARIS = "1.7.2"
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

    object Security {
        const val ROOTBEER = "0.0.8"
    }

}