package com.gtreb.healthydog.ui.navbar

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtreb.healthydog.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NavBarFragmentViewModel(private val context: Context) : ViewModel() {


    val nav1 = NavBarItemViewData().apply {
        icon.value = R.drawable.icone_accueil
        text.value = context.getString(R.string.home)
    }
    val nav2 = NavBarItemViewData().apply {
        icon.value = R.drawable.icone_aide
        text.value = context.getString(R.string.help)
    }
    val nav3 = NavBarItemViewData().apply {
        icon.value = R.drawable.icone_alertes
        text.value = context.getString(R.string.alert)
    }
    val navs = NavBarLayoutData().apply {
        navItems.value = listOf(nav1, nav2, nav3)
    }

    init {
        // scope closed automatically when viewModel die
        viewModelScope.launch(Dispatchers.Default) {
            navs.selected.collect {
                withContext(Dispatchers.Main) { toaster(navs.navItems.value?.getOrNull(it.index)?.text?.value) }
            }
        }
    }

    var toaster: (String?) -> Unit = {}

    fun selectTab(index: Int) {
        navs.selected.value = NavBarLayoutData.Event.External(index)
    }
}