package com.gtreb.healthydog.veterinaire

import android.app.Application
import android.content.Context
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class VeterinaireFragmentViewModel (
    private val context: Context,
    private val coordinator: VeterinaireCoordinator,
    application: Application,
) : AndroidViewModel(application) {

}