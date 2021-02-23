package com.gtreb.healthydog.ui.navbar

import android.content.Context
import android.util.AttributeSet
import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.ui.CustomView
import com.gtreb.healthydog.databinding.UiNavBarItemViewBinding

/**
 * A single item inside a [NavBarLayout]
 *
 * @see NavBarLayout
 * @see NavBarItemViewData
 */
class NavBarItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : CustomView<UiNavBarItemViewBinding>(context, attrs, defStyleAttr, defStyleRes) {
    override val layoutId: Int
        get() = R.layout.ui_nav_bar_item_view

    var viewData: NavBarItemViewData?
        get() = binding.viewData
        set(value) {
            binding.viewData = value
        }
}