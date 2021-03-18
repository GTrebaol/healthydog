package com.gtreb.healthydog.common.navigation

import android.content.Context
import com.gtreb.healthydog.R
import com.gtreb.healthydog.common.navigation.ui.navbar.NavBarItemViewData
import com.gtreb.healthydog.common.navigation.ui.navbar.NavBarLayoutData

class NavigationViewData(private val context: Context) {

    companion object {

        /** Special index to select nothing */
        const val INDEX_NAVIGATION_NONE = -1

        /** index of element [navigationHome] inside [navigationItems] */
        const val INDEX_NAVIGATION_HOME = 0

        /** index of element [navigationAccounts] inside [navigationItems] */
        const val INDEX_NAVIGATION_EVOLUTION = 1

        /** index of element [navigationTransfer] inside [navigationItems] */
        const val INDEX_NAVIGATION_CALENDAR = 2

        /** index of element [navigationVirtualis] inside [navigationItems] */
        const val INDEX_NAVIGATION_BARF_FOOD = 3

        /** index of element [navigationMenu] inside [navigationItems] */
        const val INDEX_NAVIGATION_EMERGENCY_VET = 4
    }


    private val navigationHome = NavBarItemViewData().apply {
        icon.value = R.drawable.icone_accueil
        text.value = context.getString(R.string.navigation_home)
        accessibilityText.value = context.getString(R.string.accessibility_navigation_home)
        testId.value = context.getString(R.string.test_navigation_home)
    }
    private val navigationEvolution = NavBarItemViewData().apply {
        icon.value = R.drawable.icone_chart
        text.value = context.getString(R.string.navigation_evolution)
        accessibilityText.value = context.getString(R.string.accessibility_navigation_evolution)
        testId.value = context.getString(R.string.test_navigation_evolution)
    }
    private val navigationCalendar = NavBarItemViewData().apply {
        icon.value = R.drawable.icone_calendrier_vaccination
        text.value = context.getString(R.string.accessibility_navigation_calendar)
        accessibilityText.value = context.getString(R.string.accessibility_navigation_calendar)
        testId.value = context.getString(R.string.test_navigation_calendar)
    }
    private val navigationBarfFood = NavBarItemViewData().apply {
        icon.value = R.drawable.icone_panier
        text.value = context.getString(R.string.accessibility_navigation_barf_food)
        accessibilityText.value = context.getString(R.string.accessibility_navigation_barf_food)
        testId.value = context.getString(R.string.test_navigation_barf_food)
    }
    private val navigationEmergencyVet = NavBarItemViewData().apply {
        icon.value = R.drawable.icone_veto
        text.value = context.getString(R.string.accessibility_navigation_emergency_vet)
        accessibilityText.value = context.getString(R.string.accessibility_navigation_emergency_vet)
        testId.value = context.getString(R.string.test_navigation_emergency_vet)
    }

    /** Container of all navigation bar items */
    // should be placed after items declaration
    val navigationItems = NavBarLayoutData().apply {
        navItems.value = listOf(navigationHome, navigationEvolution, navigationCalendar, navigationBarfFood, navigationEmergencyVet)
    }

}