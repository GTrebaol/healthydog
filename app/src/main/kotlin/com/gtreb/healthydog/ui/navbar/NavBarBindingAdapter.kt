package com.gtreb.healthydog.ui.navbar

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.gtreb.healthydog.R

object NavBarBindingAdapter {

    @JvmStatic
    @BindingAdapter("nav_item_src")
    fun ImageView.setDrawableResource(@DrawableRes drawableRes: Int?) {
        if (drawableRes == null || drawableRes == 0) {
            isVisible = false
        } else {
            isVisible = true
            setImageResource(drawableRes)
        }
    }

    @JvmStatic
    @BindingAdapter("nav_item_background")
    fun View.setDrawableBackground(@DrawableRes drawableRes: Int?) {
        val res = if (drawableRes == null || drawableRes == 0) R.drawable.bg_nav_bar_item else drawableRes
        setBackgroundResource(res)
    }
}