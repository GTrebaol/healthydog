package com.gtreb.healthydog.common.implementation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.gtreb.healthydog.common.navigation.NavigationItem
import com.gtreb.healthydog.common.navigation.NavigationPublisher

/**
 * Used by Router to navigate in Phenix Base.
 * Minimal screen portion.
 **/
abstract class CustomFragment<viewBinding : ViewDataBinding> : Fragment() {

    /** The ViewDataBinding used by the view. */
    lateinit var binding: viewBinding

    protected open val navigationItem: NavigationItem? = null
    protected open val navigationPublisher by lazy { NavigationPublisher(requireContext()) { navigationItem } }
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = lifecycle
        binding.setVariable(BR.lifecycle, lifecycle)
        lifecycle.addObserver(navigationPublisher)
        bindViewModels(binding)
        return binding.root
    }


    /**
     * This method is called when the underlying router needs to do a back on it
     * @return true when fragment consume the back
     **/
    open fun onBackPressed(): Boolean = false

    abstract fun bindViewModels(binding: viewBinding)
}
