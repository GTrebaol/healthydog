package com.gtreb.healthydog.common.navigation


import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager

/**
 * An helper handling Local broadcast for navigation sake.
 *
 * Used by UI elements, it can trigger component's start and stop broadcast events.
 */
internal object InternalNavigation {

    /** Base qualifier of actions and extra */
    private val ROOT = InternalNavigation::class.qualifiedName

    internal val BROADCAST_ACTION_START = "$ROOT.ACTION.Start"
    internal val BROADCAST_ACTION_STOP = "$ROOT.ACTION.Stop"

    internal val EXTRA_NAVIGATION_ITEM = "$ROOT.EXTRA.NavigationItem"
    internal val EXTRA_NAVIGATION_ID = "$ROOT.EXTRA.NavigationId"

    /**
     * Push a start event on the local broadcast manager
     *
     * @param context to get BroadcastManager
     * @param id of the publisher. Useful if a publisher has more than one navigationItem to present.
     * @param item Item to push on the broadcast event.
     * @see NavigationItem
     */
    @Throws(IllegalArgumentException::class)
    fun publishEventStart(context: Context, id: String, item: NavigationItem?) {
        val intent = Intent(BROADCAST_ACTION_START)
            .putExtra(EXTRA_NAVIGATION_ID, id)
            .putExtra(EXTRA_NAVIGATION_ITEM, item)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    /**
     * Push a stop event on the local broadcast manager
     *
     * @param context to get BroadcastManager
     * @param id of the publisher. Useful if a publisher has more than one navigationItem to present.
     * @see NavigationItem
     */
    @Throws(IllegalArgumentException::class)
    fun publishEventStop(context: Context, id: String) {
        val intent = Intent(BROADCAST_ACTION_STOP)
            .putExtra(EXTRA_NAVIGATION_ID, id)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    /** Simple utils to get the navigation item from intent */
    fun navigationItemFromIntent(intent: Intent): NavigationItem? = intent.getParcelableExtra(EXTRA_NAVIGATION_ITEM)

    /** Simple utils to get the navigation id from intent */
    fun navigationIdFromIntent(intent: Intent): String? = intent.getStringExtra(EXTRA_NAVIGATION_ID)
}
