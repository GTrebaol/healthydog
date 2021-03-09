package com.gtreb.healthydog.common.implementation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.gtreb.healthydog.BR
import com.gtreb.healthydog.common.navigation.NavigationItem
import com.gtreb.healthydog.common.navigation.NavigationPublisher
import com.gtreb.healthydog.utils.SettingsAction

abstract class CustomFragment<VB : ViewDataBinding> : Fragment() {

    val logger = TimberMonitor()
    lateinit var binding: VB
    abstract val layoutId: Int

    protected open val navigationItem: NavigationItem? = null
    protected open val navigationPublisher by lazy { NavigationPublisher(requireContext()) { navigationItem } }

    abstract fun bindViewModels(binding: VB)

    open val lifecycleOwner get() = viewLifecycleOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(navigationPublisher)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = lifecycleOwner
        binding.setVariable(BR.lifecycle, lifecycleOwner)
        bindViewModels(binding)
        return binding.root
    }

    open fun onBackPressed(): Boolean = false


    fun goSettings(action: SettingsAction) {
        val i = Intent(action.value)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            requireActivity().startActivity(i)
        } catch (e: ActivityNotFoundException) {
            logger.logE(e.message.toString(), e)
        }
    }
}
