package com.gtreb.healthydog.ui.navbar

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi

class NavBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    init {
        orientation = HORIZONTAL
    }

    /** lifecycleOwner used for viewData listening */
    var lifecycle: LifecycleOwner? = null
        set(value) {
            if (lifecycle == value) return
            field = value
            doUpdate()
        }

    var viewData: NavBarLayoutData? = null
        set(value) {
            if (viewData == value) return
            field = value
            doUpdate()
        }

    /**
     * Create or replace [NavBarItemView] into layout.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    // variable of name "_xyz" are warnings
    @Suppress("LocalVariableName")
    private fun doUpdate() {
        val _lifecycle = lifecycle ?: return
        val _viewData = viewData ?: return

        _viewData.navItems.observe(
                _lifecycle,
            { items ->
                items.indices.forEach { index ->
                    val tabItem = getChildAt(index) as? NavBarItemView ?: NavBarItemView(context).also {
                        it.layoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
                        addView(it)
                    }
                    tabItem.lifecycle = _lifecycle
                    tabItem.viewData = items[index].apply {
                        onClickListener = {
                            _viewData.selected.value = NavBarLayoutData.Event.User(index)
                        }
                    }
                }
                (items.size until childCount).forEach { removeViewAt(it) }
                postInvalidate()
            }
        )

        _viewData.selected.asLiveData().observe(
            _lifecycle,
            { event ->
                _viewData.navItems.value?.forEachIndexed { index, item ->
                    item.selected.postValue(event.index == index)
                }
            }
        )
    }

}