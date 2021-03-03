package com.gtreb.healthydog.ui.navbar

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow

class NavBarLayoutData {
    sealed class Event(val index: Int) {
        class User(index: Int) : Event(index)
        class External(index: Int) : Event(index)
    }

    val selected = MutableStateFlow<Event>(Event.External(0))
    val navItems = MutableLiveData<List<NavBarItemViewData>>(listOf())
}