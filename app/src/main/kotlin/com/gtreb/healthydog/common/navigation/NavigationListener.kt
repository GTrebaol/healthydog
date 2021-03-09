package com.gtreb.healthydog.common.navigation

import android.content.Context
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData

/**
 * A lifecycle aware component that listen for events on navigation
 *
 * @param context for listening to BroadcastManager
 */
class NavigationListener(private val context: Context) : LifecycleObserver {

    /**
     * Representation of active navigation's item.
     * Useful when more than one navigation elements is displayed in the same time.
     * Elements are ordered in chronological order.
     */
    val displayed = MutableLiveData<Map<String, NavigationItem?>>(linkedMapOf())
}