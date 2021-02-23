package com.gtreb.healthydog.ui.navbar

import androidx.lifecycle.MutableLiveData
import com.gtreb.healthydog.R

class NavBarItemViewData {

    companion object {
        private const val DEFAULT_TEXT: String = ""
        private val DEFAULT_ICON: Int? = null
        private val DEFAULT_TINT: Int? = null
        private const val DEFAULT_BACKGROUND: Int = R.drawable.bg_nav_bar_item
        private const val DEFAULT_IS_CLICKABLE: Boolean = true
        private const val DEFAULT_SELECTED: Boolean = false
    }

    val icon = MutableLiveData<Int?>(DEFAULT_ICON)
    val text = MutableLiveData<String>(DEFAULT_TEXT)
    val background = MutableLiveData<Int?>(DEFAULT_BACKGROUND)
    val selected = MutableLiveData<Boolean>(DEFAULT_SELECTED)

    val clickable = MutableLiveData<Boolean>(DEFAULT_IS_CLICKABLE)
    var onClickListener: () -> Unit = {}

    val accessibilityText = MutableLiveData<String>(DEFAULT_TEXT)
}