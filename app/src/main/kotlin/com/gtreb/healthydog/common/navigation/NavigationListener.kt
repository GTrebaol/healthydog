package com.gtreb.healthydog.common.navigation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.gtreb.healthydog.common.navigation.InternalNavigation.BROADCAST_ACTION_START
import com.gtreb.healthydog.common.navigation.InternalNavigation.BROADCAST_ACTION_STOP

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

    /**
     * Start event listener for navigation items.
     * Add to [displayed] received events.
     */
    private val startEventReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action != BROADCAST_ACTION_START) return
            val id: String = InternalNavigation.navigationIdFromIntent(intent) ?: return
            val item = InternalNavigation.navigationItemFromIntent(intent)

            val items = (displayed.value ?: linkedMapOf())
            (items as LinkedHashMap)[id] = item
            displayed.value = items
        }
    }

    /**
     * Stop event listener for navigation items.
     * Remove from [displayed] received events.
     */
    private val stopEventReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action != BROADCAST_ACTION_STOP) return
            val id: String = InternalNavigation.navigationIdFromIntent(intent) ?: return

            val items = (displayed.value ?: linkedMapOf())
            (items as LinkedHashMap).remove(id)
            displayed.value = items
        }
    }

    private val startFilter = IntentFilter(BROADCAST_ACTION_START)
    private val stopFilter = IntentFilter(BROADCAST_ACTION_STOP)
    private val broadcastManager get() = LocalBroadcastManager.getInstance(context)

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun registerNavigationListener() {
        broadcastManager.registerReceiver(startEventReceiver, startFilter)
        broadcastManager.registerReceiver(stopEventReceiver, stopFilter)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun unregisterNavigationListener() {
        broadcastManager.unregisterReceiver(startEventReceiver)
        broadcastManager.unregisterReceiver(stopEventReceiver)
    }
}