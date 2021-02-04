package com.gtreb.healthydog

import android.app.Activity
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.koin.core.KoinComponent
import java.lang.ref.WeakReference

/** It holds activity reference */

class ActivityHolder(private var context: Context) : LifecycleObserver, KoinComponent {
    private var _activity: WeakReference<Activity?> = WeakReference(null)
    val activity get() = _activity.get()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun activityAvailable(owner: LifecycleOwner) {
        if (owner !is Activity) return
        _activity = WeakReference(owner)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun activityUnAvailable(owner: LifecycleOwner) {
        if (activity == owner) _activity.clear()
    }

    // region InternalNavigation


}