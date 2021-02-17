package com.gtreb.healthydog.common.implementation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.gtreb.healthydog.common.navigation.NavigationItem
import com.gtreb.healthydog.common.navigation.NavigationPublisher

/**
 * Used by Router to navigate in Phenix Base.
 * Minimal screen portion.
 **/
abstract class CustomFragment<ViewBinding : ViewDataBinding> : Fragment() {

    /**
     * Item published by [navigationPublisher]
     * @see NavigationItem
     */
    protected open val navigationItem: NavigationItem? = null
    protected open val navigationPublisher by lazy { NavigationPublisher(requireContext()) { navigationItem } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(navigationPublisher)
    }

    /**
     * This method is called when the underlying router needs to do a back on it
     * @return true when fragment consume the back
     **/
    open fun onBackPressed(): Boolean = false

    abstract fun bindViewModels(binding: VB)
}
