package com.gtreb.healthydog.evolution

import android.app.Application
import android.content.Context
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EvolutionFragmentViewModel (
    private val context: Context,
    private val coordinator: EvolutionCoordinator,
    application: Application,
) : AndroidViewModel(application) {

    private val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val liveIsLoading: LiveData<Boolean> = isLoading


    fun load() {
        isLoading.value = true
        Handler().postDelayed({
            isLoading.postValue(false)
        }, 2000)
    }

}