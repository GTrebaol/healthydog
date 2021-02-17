package com.gtreb.healthydog.common.implementation


import android.annotation.SuppressLint
import android.util.Log
import com.gtreb.healthydog.BuildConfig
import com.gtreb.healthydog.common.interfaces.ITechnicalModuleAction
import com.gtreb.healthydog.common.interfaces.ITechnicalModuleCallback
import timber.log.Timber
import timber.log.Timber.DebugTree


open class MyDebugTree : DebugTree() {

    companion object {
        const val STACKTRACE_TAG_POSITION = 7
    }

    override fun isLoggable(tag: String?, priority: Int): Boolean {

        /**
         * No Log in VERBOSE OR DEBUG if monitoring is builded in DEBUG config
         */
        if (!BuildConfig.DEBUG && (priority == Log.DEBUG || priority == Log.VERBOSE))
            return false

        return super.isLoggable(tag, priority)
    }

    override fun createStackElementTag(element: StackTraceElement): String? {
        return super.createStackElementTag(Throwable().stackTrace[STACKTRACE_TAG_POSITION])
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


class TimberMonitor : LoggingAction() {

    companion object {
        const val TAG = "GTREB"
    }

    init {
        /**
         * Always plant the tree for error purpose
         */
        Timber.tag(TAG)
        Timber.plant(MyDebugTree())
    }

    @SuppressLint("WrongLogger")
    override fun logV(message: String) {
        if (BuildConfig.DEBUG) Timber.v(message)
    }

    @SuppressLint("WrongLogger")
    override fun logD(message: String) {
        if (BuildConfig.DEBUG) Timber.d(message)
    }

    @SuppressLint("WrongLogger")
    override fun logE(message: String, throwable: Throwable?) {
        Timber.e(throwable, message)
    }
}
