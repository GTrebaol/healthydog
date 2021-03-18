package com.gtreb.healthydog.modules.dashboard.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.gtreb.healthydog.modules.dashboard.DashboardCoordinator

class DashboardFragmentViewModel (
    private val context: Context,
    private val coordinator: DashboardCoordinator,
    application: Application,
) : AndroidViewModel(application) {

}