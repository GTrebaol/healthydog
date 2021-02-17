package com.gtreb.healthydog.dashboard

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DashboardFragmentViewModel (
    private val context: Context,
    private val coordinator: DashboardCoordinator,
    application: Application,
) : AndroidViewModel(application) {

    private val isLoading = MutableLiveData<Boolean>().apply { value = true }
    val liveIsLoading: LiveData<Boolean> = isLoading

}