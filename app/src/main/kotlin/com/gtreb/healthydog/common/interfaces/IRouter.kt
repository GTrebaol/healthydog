package com.gtreb.healthydog.common.interfaces


import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.gtreb.healthydog.utils.toBundle

interface IRouter {

    companion object {
        const val FRAGMENT_MANAGER = "IRouter.FragmentManager"
        const val CONTAINER_ID = "IRouter.ContainerID"
    }

    /**
     * Initialize method
     * Depending to the needs of the application, elements are optional and configurable
     * @param arguments Can be anything
     */
    fun init(arguments: Map<String, Any?> = mapOf())

    /**
     * Show a fragment to the navigation stack
     * @param fragment A fragment class that will be instantiate by the system.
     * @param arguments All arguments for this fragment. The map is transformed automatically into a bundle
     * @param isRoot place [fragment] at root’s navigation
     */
    fun <T : Fragment> show(
        fragment: Class<T>,
        arguments: Map<String, Any?>,
        isRoot: Boolean = false
    ) {
        show(fragment, arguments.toBundle(), isRoot)
    }

    // retro-compatibility
    fun <T : Fragment> show(fragment: Class<T>, arguments: Map<String, Any?>) =
        show(fragment, arguments.toBundle(), false)

    /**
     * Show a fragment to the navigation stack
     * @param fragment A fragment class that will be instantiate by the system.
     * @param bundle All arguments for this fragment.
     * @param isRoot place [fragment] at root’s navigation
     */
    fun <T : Fragment> show(fragment: Class<T>, bundle: Bundle? = null, isRoot: Boolean = false)

    // retro-compatibility
    fun <T : Fragment> show(fragment: Class<T>, bundle: Bundle? = null) =
        show(fragment, bundle, false)

    /**
     * Check if the current fragment is inside the navigation’s stack.
     * @param fragment the target to check
     * @return true when fragment is in the stack
     */
    fun <T : Fragment> isShown(fragment: Class<T>): Boolean

    /**
     * Show a fragment over the current navigation.
     * If the fragment is already on screen it override the targetFragment with the new [caller]
     *
     * @param dialogClass the DialogFragment to show on screen.
     * @param bundle All arguments for this fragment.
     * @param caller the caller’s fragment. The generated dialog fragment can use [Fragment.getTargetFragment] to notify the caller.
     * @param requestCode can be used by the generated dialog fragment with [Fragment.getTargetFragment] to call [Fragment.onActivityResult]
     * @return true if the dialog is created
     */
    // TODO : This function will be deprecated when androidx.fragment 1.3.0 will release.
    fun <T : DialogFragment> showOverlay(
        dialogClass: Class<T>,
        bundle: Bundle? = null,
        caller: Fragment? = null,
        requestCode: Int = 0
    ): Boolean

    /**
     * Do a back on the latest presented Fragment
     * @return the number of fragments remaining in the stack or null if the fragment has consumed the back
     **/
    fun doBack(): Int?

    /**
     * Go back to a specific fragment
     */
    fun <T : Fragment> goBack(fragment: Class<T>, inclusive: Boolean = false): Boolean

    /**
     * Check if router has no current navigation
     * @return true if empty
     */
    fun isEmpty(): Boolean

    /**
     * Get fragment that is on top of backstack
     */
    fun getCurrentFragment(): Fragment?
}
