package com.gtreb.healthydog.common.implementation

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.gtreb.healthydog.common.interfaces.IRouter
import org.koin.core.KoinComponent
import org.koin.core.inject

class Router : IRouter, KoinComponent {

    private val monitor: TimberMonitor by inject()

    companion object {
        /**
         * Use this backstack entry to add fragment as root
         * Prevent app to close if there is only root fragment on the backstack
         */
        private const val ROOT_BACK_STACK_NAME = "ROOT_BACK_STACK_NAME"
    }

    /** Exception throw when initialization failed */
    class InitializationException : IllegalArgumentException(
        """
        Map arguments must have :
            - ${IRouter.FRAGMENT_MANAGER} (IRouter.FRAGMENT_MANAGER) a fragment manager
            - ${IRouter.CONTAINER_ID} (IRouter.CONTAINER_ID) a layout id to place fragments inside
        """.trimIndent()
    )

    /** Exception throw when a method is called before init */
    class RouterNotInitialized : IllegalStateException("Please call IRouter.init() before any methods")

    private var fragmentManager: FragmentManager? = null
    private var containerId: Int? = null

    /**
     * Initialize method defined in the interface
     * @param arguments the fragment manager and containerId to use for navigation
     */
    @Throws(InitializationException::class)
    override fun init(arguments: Map<String, Any?>) {
        this.fragmentManager = arguments[IRouter.FRAGMENT_MANAGER] as? FragmentManager ?: throw InitializationException()
        this.containerId = arguments[IRouter.CONTAINER_ID] as? Int ?: throw InitializationException()
    }

    /**
     * Called by all methods before executing
     * @return A pair of [FragmentManager] and ContainerId
     */
    @Throws(RouterNotInitialized::class)
    private fun requireInit(): Pair<FragmentManager, Int> =
        (fragmentManager ?: throw RouterNotInitialized()) to (containerId ?: throw RouterNotInitialized())

    /**
     * Show a fragment to the navigation stack
     * @param fragment A fragment class that will be instantiate by the system.
     * @param bundle All arguments for this fragment.
     * @param isRoot place [fragment] at root’s navigation
     */
    @Throws(RouterNotInitialized::class, IllegalStateException::class, IllegalArgumentException::class)
    override fun <T : Fragment> show(fragment: Class<T>, bundle: Bundle?, isRoot: Boolean) {
        val (fragmentManager, containerId) = requireInit()
        var backstackName = fragment.name

        if (isRoot) {
            backstackName = ROOT_BACK_STACK_NAME
            fragmentManager.popBackStackImmediate(ROOT_BACK_STACK_NAME, 0)
        }

        monitor.logD("Backstack name = $backstackName")

        fragmentManager.beginTransaction()
            .replace(containerId, fragment, bundle)
            .addToBackStack(backstackName)
            .commitAllowingStateLoss()
    }

    /**
     * Check if the current fragment is inside the navigation’s stack.
     * @param fragment the target to check
     * @return true when fragment is in the stack
     */
    @Throws(RouterNotInitialized::class)
    override fun <T : Fragment> isShown(fragment: Class<T>): Boolean {
        val (fragmentManager, _) = requireInit()
        repeat(fragmentManager.backStackEntryCount) {
            if (fragmentManager.getBackStackEntryAt(it).name == fragment.name) return true
        }
        return false
    }

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
    @Suppress("UNCHECKED_CAST") // unchecked is handled
    @Throws(RouterNotInitialized::class)
    override fun <T : DialogFragment> showOverlay(dialogClass: Class<T>, bundle: Bundle?, caller: Fragment?, requestCode: Int): Boolean {
        val (fragmentManager, _) = requireInit()
        val tag = dialogClass.name
        val dialogFragment = fragmentManager.findFragmentByTag(tag) as? T ?: dialogClass.newInstance()
        dialogFragment.arguments = bundle
        dialogFragment.setTargetFragment(caller, requestCode)
        if (dialogFragment.isAdded) return false
        dialogFragment.show(fragmentManager, tag)
        return true
    }

    /** Do a back on the fragment */
    @Throws(RouterNotInitialized::class, IllegalStateException::class)
    override fun doBack(): Int? {
        val (fragmentManager, _) = requireInit()
        val fragment = fragmentManager.fragments.lastOrNull()
        if (fragment is CustomFragment<*> && fragment.onBackPressed()) {
            return null
        } else {
            fragmentManager.popBackStackImmediate()
        }
        return fragmentManager.fragments.size
    }

    /**
     * Go back to a specific fragment
     *
     * @param inclusive If set, then all matching entries will
     * be consumed until one that doesn't match is found or the bottom of
     * the stack is reached. Otherwise, all entries up to but not including that entry
     * will be removed. By default inclusive is not set.
     */
    @Throws(RouterNotInitialized::class)
    override fun <T : Fragment> goBack(fragment: Class<T>, inclusive: Boolean): Boolean {
        val (fragmentManager, _) = requireInit()
        return fragmentManager.popBackStackImmediate(fragment.name, if (inclusive) POP_BACK_STACK_INCLUSIVE else 0)
    }

    /** Check if navigation has fragment inside */
    @Throws(RouterNotInitialized::class, IllegalStateException::class)
    override fun isEmpty(): Boolean {
        val (fragmentManager, _) = requireInit()
        return fragmentManager.fragments.size == 0
    }

    /** Get fragment that is on top of backstack */
    @Throws(RouterNotInitialized::class, IllegalStateException::class)
    override fun getCurrentFragment(): Fragment? {
        val (fragmentManager, _) = requireInit()
        return fragmentManager.fragments.lastOrNull()
    }
}