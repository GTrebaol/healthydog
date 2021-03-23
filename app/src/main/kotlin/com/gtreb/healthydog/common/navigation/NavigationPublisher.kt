package com.gtreb.healthydog.common.navigation

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.UUID

/**
 * A lifecycle aware component that publish [NavigationItem] events.
 *
 * A publisher can override existing navigation by sending a second event with [publishEventStart]
 *
 * @param context for publishing on BroadcastManager
 * @param publisherId Publisher id.
 * @param navigationItemProvider NavigationItem of this publisher. Should return navigation item to publish when component start.
 */
class NavigationPublisher(
    private val context: Context,
    private val publisherId: String = UUID.randomUUID().toString(),
    private val navigationItemProvider: () -> NavigationItem?
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun publishEventStart() = publishEventStart(navigationItemProvider())
    private fun publishEventStart(item: NavigationItem?) = InternalNavigation.publishEventStart(context, publisherId, item)

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun publishEventStop() = InternalNavigation.publishEventStop(context, publisherId)
}
