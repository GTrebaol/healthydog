package com.gtreb.healthydog.common.implementation

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


abstract class LoggingAction : ITechnicalModuleAction<LoggingCallback>() {

    abstract fun logV(message: String)
    abstract fun logD(message: String)
    abstract fun logE(message: String, throwable: Throwable? = null)
}

interface LoggingCallback : ITechnicalModuleCallback


class TimberMonitor : LoggingAction() {

    companion object {
        const val TAG = "GTREB"
    }

    init {
        Timber.tag(TAG)
        Timber.plant(MyDebugTree())
    }

    override fun logV(message: String) {
        if (BuildConfig.DEBUG) Timber.v(message)
    }

    override fun logD(message: String) {
        if (BuildConfig.DEBUG) Timber.d(message)
    }

    override fun logE(message: String, throwable: Throwable?) {
        Timber.e(throwable, message)
    }
}
