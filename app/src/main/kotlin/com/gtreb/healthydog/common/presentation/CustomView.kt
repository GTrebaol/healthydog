package com.gtreb.healthydog.common.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.gtreb.healthydog.BR

abstract class CustomView <VB : ViewDataBinding> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    open var lifecycle: LifecycleOwner?
        get() = binding.lifecycleOwner
        set(value) {
            binding.lifecycleOwner = value
            binding.setVariable(BR.lifecycle, lifecycle)
            value?.let(this::onLifeCycleOwnerSet)
        }

    open val binding: VB =
        DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, this, true)

    /** For customView who want to get a notification when lifecycleOwner is set */
    open fun onLifeCycleOwnerSet(lifecycleOwner: LifecycleOwner) = Unit

    /** The resource layout Id used by extended view */
    abstract val layoutId: Int

    init {
        // Disable clipping for child view.
        // A cardView shadow will be clip if those variables are not set to false
        clipToPadding = false
        clipChildren = false
    }



}
