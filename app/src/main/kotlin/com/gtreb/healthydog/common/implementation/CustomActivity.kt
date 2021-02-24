package com.gtreb.healthydog.common.implementation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gtreb.healthydog.BR

abstract class CustomActivity<VB: ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: VB
    abstract val layoutId: Int
    abstract fun bindViewModels(binding: VB)
    open val lifecycleOwner get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = lifecycleOwner
        binding.setVariable(BR.lifecycle, lifecycleOwner)
        bindViewModels(binding)
    }
}
