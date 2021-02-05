package com.gtreb.healthydog.common


import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.QualifierValue

/**
 * Define all the technical entry points for monitoring technical module
 * - [LoggingAction]
 */
object IMonitorTechnicalModule {

    object Params {
        object Timber : Qualifier {
            override val value: QualifierValue = "Timber"
        }

        object AndroidUtil : Qualifier {
            override val value: QualifierValue = "AndroidUtil"
        }
    }

    /**
     * Define all the technical entry points for logging
     * No callbacks needed for logging
     */
    abstract class LoggingAction : ITechnicalModuleAction<LoggingCallback>() {
        /**
         * Print the message in parameter in the logcat in verbose mode
         * @param message the messaged printed
         */
        abstract fun logV(message: String)

        /**
         * Print the message in parameter in the logcat in debug mode
         * @param message the messaged printed
         */
        abstract fun logD(message: String)

        /**
         * Print the message and throwable in parameter in the logcat in error mode
         * @param message the messaged printed
         * @param throwable the Throwable printed
         */
        abstract fun logE(message: String, throwable: Throwable? = null)
    }

    interface LoggingCallback : ITechnicalModuleCallback
}
