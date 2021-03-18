package com.gtreb.healthydog.common.navigation.logic

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gtreb.healthydog.AppCoordinator
import com.gtreb.healthydog.common.navigation.IDispatcherService
import com.gtreb.healthydog.common.navigation.NavigationViewData
import com.gtreb.healthydog.common.navigation.NavigationViewData.Companion.INDEX_NAVIGATION_BARF_FOOD
import com.gtreb.healthydog.common.navigation.NavigationViewData.Companion.INDEX_NAVIGATION_CALENDAR
import com.gtreb.healthydog.common.navigation.NavigationViewData.Companion.INDEX_NAVIGATION_EMERGENCY_VET
import com.gtreb.healthydog.common.navigation.NavigationViewData.Companion.INDEX_NAVIGATION_EVOLUTION
import com.gtreb.healthydog.common.navigation.NavigationViewData.Companion.INDEX_NAVIGATION_HOME
import com.gtreb.healthydog.common.navigation.ui.navbar.NavBarLayoutData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HealthyDogActivityViewModel (
    private val coordinator: AppCoordinator,
    private val dispatchers: IDispatcherService,
    private val context: Context,
    val navigation: NavigationViewData
) : ViewModel() {

    var selected
        get() = navigation.navigationItems.selected.value.index
        set(value) = selectNavBarIndex(value)

    private var lastIndex: NavBarLayoutData.Event? = null
    private var drawerIndex: NavBarLayoutData.Event? = null

    init {
        viewModelScope.launch(dispatchers.default) {
            navigation.navigationItems.selected.collect{ onNavigationUpdate(it) }
        }
        lastIndex = navigation.navigationItems.selected.value
        drawerIndex = lastIndex
    }

    private fun selectNavBarIndex(index: Int) = selectNavBarIndex(NavBarLayoutData.Event.External(index))
    private fun selectNavBarIndex(event: NavBarLayoutData.Event) {
        navigation.navigationItems.selected.value = event
    }

    private fun onNavigationUpdate(event: NavBarLayoutData.Event) = viewModelScope.launch(dispatchers.main) {
        when (event) {
            is NavBarLayoutData.Event.User -> {
                when (event.index) {
                    INDEX_NAVIGATION_HOME -> {
                        coordinator.goToDashboard()
                    }
                    INDEX_NAVIGATION_EVOLUTION -> {
                        coordinator.goToEvolution()
                    }
                    INDEX_NAVIGATION_CALENDAR -> {
                        coordinator.goToCalendar()
                    }
                    INDEX_NAVIGATION_BARF_FOOD -> {
                        coordinator.goToBarf()
                    }
                    INDEX_NAVIGATION_EMERGENCY_VET -> {
                        coordinator.goToEmergencyVet()
                    }
                    else -> throw IllegalArgumentException("Please check NavigationViewData.navigationItems.")
                }
            }
            // Means navigation has been updated externally
            // No actions required
            is NavBarLayoutData.Event.External -> Unit
        }
        lastIndex = event
    }
}